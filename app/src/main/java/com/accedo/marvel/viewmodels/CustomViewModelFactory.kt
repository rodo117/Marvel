package com.accedo.marvel.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CustomViewModelFactory(private val isCellphone: Boolean) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CharactersViewModel(isCellphone) as T
    }

}