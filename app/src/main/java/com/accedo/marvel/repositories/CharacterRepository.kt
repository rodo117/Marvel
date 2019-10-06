package com.accedo.marvel.repositories


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.accedo.marvel.data.Character
import java.time.ZoneOffset


class CharacterRepository() {

    private val manageDataContract:ManageDataContract = NetworkCall()
    var allCharacters: LiveData<List<Character>> = manageDataContract.getCharacters()

    fun getCharacters(offset: Int) {
        allCharacters = manageDataContract.getCharacters(offset)
    }

}
