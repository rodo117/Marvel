package com.accedo.marvel.viewmodels

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.accedo.marvel.data.Character
import com.accedo.marvel.factories.PagedConfigurationFactory
import com.accedo.marvel.repositories.CharactersRepository
import com.accedo.marvel.repositories.CharactersRepositoryImplementation
import com.accedo.marvel.repositories.FavoriteCharacterRepositoryImplementation
import com.accedo.marvel.room.database.CharactersRoomDataBase
import com.accedo.marvel.room.entities.FavoriteCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersViewModel(isCellphone: Boolean, application: Application) : AndroidViewModel(application) {

    val liveDataCharacters: LiveData<PagedList<Character>>

    val favoriteCharactersLiveData: LiveData<List<FavoriteCharacter>>
    private val favoriteCharactersRepository: FavoriteCharacterRepositoryImplementation

    lateinit var liveDataComics: LiveData<PagedList<Character>>
    private val modelFactory = PagedConfigurationFactory()
    private val repository:CharactersRepository = CharactersRepositoryImplementation()

    val liveDataCharacterSelected: MutableLiveData<Character> by lazy {
        MutableLiveData<Character>()
    }

    init {
        liveDataCharacters = repository.getCharacters(modelFactory.getConfiguration(isCellphone)).build()

        val favoritesCharactersDao = CharactersRoomDataBase.getDatabase(application).favoriteCharactersDao()
        favoriteCharactersRepository = FavoriteCharacterRepositoryImplementation(favoritesCharactersDao)
        favoriteCharactersLiveData = favoriteCharactersRepository.favoriteCharactersLiveData
    }

    fun initLiveDataComics(character: Character){
        liveDataComics = repository.getCharacters(modelFactory.getConfiguration(false),character).build()
    }

    fun favoriteCharacterClicked(characterId: String, isChecked:Boolean) = viewModelScope.launch(Dispatchers.IO) {
        if(isChecked) {
            favoriteCharactersRepository.insertFavoriteCharacter(FavoriteCharacter(characterId))
        }else {
            favoriteCharactersRepository.deleteFavoriteCharacter(FavoriteCharacter(characterId))
        }
    }

}