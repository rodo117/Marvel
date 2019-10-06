package com.accedo.marvel.repositories

import androidx.lifecycle.LiveData
import com.accedo.marvel.data.Character


class CharacterRepository() {

    private val manageDataContract:ManageDataContract = NetworkCall()

    val allCharacters:LiveData<List<Character>> = manageDataContract.getCharacters()

}
