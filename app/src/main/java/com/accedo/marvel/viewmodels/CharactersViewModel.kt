package com.accedo.marvel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.accedo.marvel.data.Character
import com.accedo.marvel.repositories.CharacterRepository

class CharactersViewModel: ViewModel() {

    private val repository: CharacterRepository
    val characters: LiveData<List<Character>>

    init {
        repository = CharacterRepository()
        characters = repository.allCharacters
    }

}