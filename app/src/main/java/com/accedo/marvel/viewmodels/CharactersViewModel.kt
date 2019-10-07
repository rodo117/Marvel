package com.accedo.marvel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.accedo.marvel.data.Character
import com.accedo.marvel.datasources.CharactersDataSource


class CharactersViewModel(): ViewModel( ) {

    val PAGES_SIZE = 10

    val liveDataCharacters:LiveData<PagedList<Character>>

    val liveDataCharacterSelected: MutableLiveData<Character> by lazy {
        MutableLiveData<Character>()
    }

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGES_SIZE)
            .setEnablePlaceholders(false)
            .build()
         liveDataCharacters = initializedPagedListBuilder(config).build()
    }

    private fun initializedPagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, Character> {

        val dataSourceFactory = object : DataSource.Factory<Int, Character>() {
            override fun create(): DataSource<Int, Character> {
                return CharactersDataSource()
            }
        }
        return LivePagedListBuilder<Int, Character>(dataSourceFactory, config)
    }
}