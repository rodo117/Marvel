package com.comics.marvel.repositories

interface YoutubeCharactersRepository {
    suspend fun getCharacterVideos(characterName:String)
}