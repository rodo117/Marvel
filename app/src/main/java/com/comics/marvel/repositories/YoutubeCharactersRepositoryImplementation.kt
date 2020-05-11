package com.comics.marvel.repositories

import androidx.lifecycle.MutableLiveData
import com.comics.marvel.data.youtubeapi.Video
import com.comics.marvel.network.youtubevideos.CharacterVideosAPI

class YoutubeCharactersRepositoryImplementation:YoutubeCharactersRepository {

    val mutableLiveDataYoutubeVideos: MutableLiveData<List<Video>> by lazy {
        MutableLiveData<List<Video>>()
    }

     override suspend fun getCharacterVideos(characterName: String) {
       mutableLiveDataYoutubeVideos.postValue(
           CharacterVideosAPI().getVideos(characterName))
    }

}