package com.accedo.marvel.repositories



import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.accedo.marvel.data.Character
import com.accedo.marvel.datasources.CharacterDetailsDataSource
import com.accedo.marvel.datasources.CharactersDataSource


class CharactersRepositoryImplementation:CharactersRepository {

    override fun getCharacters(config: PagedList.Config, character: Character?): LivePagedListBuilder<Int, Character> {

        val dataSourceFactory = object : DataSource.Factory<Int, Character>() {
            override fun create(): DataSource<Int, Character> {
                return if (character!=null) {
                    CharacterDetailsDataSource(character!!)
                } else {
                    CharactersDataSource()
                }
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

}
