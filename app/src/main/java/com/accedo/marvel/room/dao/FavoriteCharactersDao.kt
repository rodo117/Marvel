package com.accedo.marvel.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.accedo.marvel.room.entities.FavoriteCharacter

@Dao
interface FavoriteCharactersDao {

    @Query("SELECT * from favorite_characters")
    fun getFavoriteCharacters(): LiveData<List<FavoriteCharacter>>

    @Delete
    fun deleteCharacter(character: FavoriteCharacter)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteCharacter: FavoriteCharacter)

}