package com.comics.marvel.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.comics.marvel.room.dao.FavoriteCharactersDao
import com.comics.marvel.room.entities.FavoriteCharacter

@Database(entities = arrayOf(FavoriteCharacter::class), version = 1, exportSchema = false)
public abstract class CharactersRoomDataBase : RoomDatabase() {

    abstract fun favoriteCharactersDao(): FavoriteCharactersDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CharactersRoomDataBase? = null

        fun getDatabase(context: Context): CharactersRoomDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharactersRoomDataBase::class.java,
                    "characters_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}