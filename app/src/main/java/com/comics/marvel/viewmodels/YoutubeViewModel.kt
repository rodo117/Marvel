package com.comics.marvel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comics.marvel.data.youtubeapi.Video
import com.comics.marvel.repositories.YoutubeCharactersRepositoryImplementation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class YoutubeViewModel:ViewModel() {

    val liveDataVideos: LiveData<List<Video>>
    private val repository : YoutubeCharactersRepositoryImplementation =
        YoutubeCharactersRepositoryImplementation()

    init {
        liveDataVideos = repository.mutableLiveDataYoutubeVideos
         }

     fun getCharacterVideos(characterName: String){
         viewModelScope.launch(Dispatchers.IO) {
             repository.getCharacterVideos(characterName)
         }
    }

}