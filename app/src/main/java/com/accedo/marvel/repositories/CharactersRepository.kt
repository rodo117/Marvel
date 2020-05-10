package com.accedo.marvel.repositories

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.accedo.marvel.data.Character

interface CharactersRepository {
    fun getCharacters(config: PagedList.Config, character: Character? = null): LivePagedListBuilder<Int, Character>
}