package com.accedo.marvel.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.accedo.marvel.data.Character

interface ManageDataContract {
    fun getCharacters(offset: Int = 0): MutableLiveData<List<Character>>
}