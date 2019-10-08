package com.accedo.marvel.repositories



import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.accedo.marvel.data.Character
import com.accedo.marvel.datasources.CharacterDetailsDataSource
import com.accedo.marvel.datasources.CharactersDataSource


class Repository() {

    fun getLivePagedListBuilder(config: PagedList.Config, character: Character? = null): LivePagedListBuilder<Int, Character> {

        val dataSourceFactory = object : DataSource.Factory<Int, Character>() {
            override fun create(): DataSource<Int, Character> {
                return if (character!=null) {
                    CharacterDetailsDataSource(character!!)
                } else {
                    CharactersDataSource()
                }
            }
        }
        return LivePagedListBuilder<Int, Character>(dataSourceFactory, config)
    }

}
