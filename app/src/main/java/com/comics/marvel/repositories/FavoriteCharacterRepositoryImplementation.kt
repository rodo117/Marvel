package com.comics.marvel.repositories

import com.comics.marvel.room.dao.FavoriteCharactersDao
import com.comics.marvel.room.entities.FavoriteCharacter

class FavoriteCharacterRepositoryImplementation(private val dao: FavoriteCharactersDao):FavoriteCharactersRepository {

     val favoriteCharactersLiveData = dao.getFavoriteCharacters()

    override suspend fun insertFavoriteCharacter(favoriteCharacter: FavoriteCharacter) {
        dao.insert(favoriteCharacter)
    }

    override suspend fun deleteFavoriteCharacter(favoriteCharacter: FavoriteCharacter) {
        dao.deleteCharacter(favoriteCharacter)
    }

}