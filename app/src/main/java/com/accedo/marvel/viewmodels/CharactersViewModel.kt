package com.accedo.marvel.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.accedo.marvel.data.Character
import com.accedo.marvel.data.Comic
import com.accedo.marvel.datasources.CharacterDetailsDataSource
import com.accedo.marvel.datasources.CharactersDataSource
import com.google.gson.JsonArray


class CharactersViewModel() : ViewModel() {

    val PAGES_SIZE = 10

    val liveDataCharacters: LiveData<PagedList<Character>>

    var liveDataComics: LiveData<PagedList<Character>>

    val liveDataCharacterSelected: MutableLiveData<Character> by lazy {
        MutableLiveData<Character>()
    }

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGES_SIZE)
            .setEnablePlaceholders(false)
            .build()
        liveDataCharacters = initializedPagedListBuilder(config).build()
        liveDataComics = initializedPagedListBuilderComic(config,Character(name="", image = "") ).build()
    }

    fun initLiveDataComics(character: Character){
        val config = PagedList.Config.Builder()
            .setPageSize(PAGES_SIZE)
            .setEnablePlaceholders(false)
            .build()
        liveDataComics = initializedPagedListBuilderComic(config, character).build()
    }

    private fun initializedPagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, Character> {

        val dataSourceFactory = object : DataSource.Factory<Int, Character>() {
            override fun create(): DataSource<Int, Character> {
                return CharactersDataSource()
            }
        }
        return LivePagedListBuilder<Int, Character>(dataSourceFactory, config)
    }

    private fun initializedPagedListBuilderComic(config: PagedList.Config, character: Character): LivePagedListBuilder<Int, Character> {

        val dataSourceFactory = object : DataSource.Factory<Int, Character>() {
            override fun create(): DataSource<Int, Character> {
                return CharacterDetailsDataSource(character)
            }
        }
        return LivePagedListBuilder<Int, Character>(dataSourceFactory, config)
    }
}