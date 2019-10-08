package com.accedo.marvel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.accedo.marvel.data.Character
import com.accedo.marvel.data.State
import com.accedo.marvel.datasources.CharactersDataSource
import com.accedo.marvel.factories.PagedConfigurationFactory
import com.accedo.marvel.repositories.Repository


class CharactersViewModel(isCellphone: Boolean) : ViewModel() {

    val liveDataCharacters: LiveData<PagedList<Character>>

    var liveDataComics: LiveData<PagedList<Character>>
    private val modelFactory = PagedConfigurationFactory()
    val repository = Repository()

    val liveDataCharacterSelected: MutableLiveData<Character> by lazy {
        MutableLiveData<Character>()
    }

    init {
        liveDataCharacters = repository.getLivePagedListBuilder(modelFactory.getConfiguration(isCellphone)).build()
        liveDataComics = repository.getLivePagedListBuilder(modelFactory.getConfiguration(false),Character(name="", image = "") ).build()
    }

    fun initLiveDataComics(character: Character){
        liveDataComics = repository.getLivePagedListBuilder(modelFactory.getConfiguration(false),character).build()
    }

}