package com.accedo.marvel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.accedo.marvel.data.Character
import com.accedo.marvel.repositories.CharacterRepository

class CharactersViewModel: ViewModel() {

    private val repository: CharacterRepository = CharacterRepository()
    val characters: LiveData<List<Character>>
    var offsetCounter = 0

    init {
        characters = repository.allCharacters
    }

    fun getCharacteres(){
        offsetCounter  = offsetCounter + 10
        repository.getCharacters(offsetCounter)
    }

}