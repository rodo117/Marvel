package com.comics.marvel.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_characters")
data class FavoriteCharacter(@PrimaryKey @ColumnInfo(name = "id") val id: String)