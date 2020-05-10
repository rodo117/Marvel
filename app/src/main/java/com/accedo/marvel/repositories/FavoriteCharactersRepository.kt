package com.accedo.marvel.repositories

import com.accedo.marvel.room.entities.FavoriteCharacter

interface FavoriteCharactersRepository {
    suspend fun insertFavoriteCharacter(favoriteCharacter: FavoriteCharacter)
    suspend fun deleteFavoriteCharacter(favoriteCharacter: FavoriteCharacter)
}