package com.comics.marvel.repositories

import com.comics.marvel.room.entities.FavoriteCharacter

interface FavoriteCharactersRepository {
    suspend fun insertFavoriteCharacter(favoriteCharacter: FavoriteCharacter)
    suspend fun deleteFavoriteCharacter(favoriteCharacter: FavoriteCharacter)
}