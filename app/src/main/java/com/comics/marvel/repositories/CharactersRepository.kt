package com.comics.marvel.repositories

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.comics.marvel.data.marvelapi.Character

interface CharactersRepository {
    fun getCharacters(config: PagedList.Config, character: Character? = null): LivePagedListBuilder<Int, Character>
}