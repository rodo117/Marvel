package com.accedo.marvel.datasources

import com.accedo.marvel.ApiService
import com.accedo.marvel.data.Character
import com.accedo.marvel.data.CharactersResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.paging.PageKeyedDataSource
import com.accedo.marvel.BuildConfig
import io.reactivex.rxjava3.core.Observable

open class CharactersDataSource : PageKeyedDataSource<Int, Character>() {


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {
        callback.onResult(getCharactersRequestList(0), null, 2)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        callback.onResult(getCharactersRequestList((params.key + 1) * BuildConfig.LIMIT), params.key + 1)

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
    }


    open fun getCharactersRequestList(offset: Int): MutableList<Character> {
        var list: MutableList<Character> = ArrayList()
        try {
            val call = getRetrofit().create(ApiService::class.java).getCharacters(
                BuildConfig.TS,
                BuildConfig.API_KEY,
                BuildConfig.HASH,
                BuildConfig.LIMIT,
                offset
            ).execute()

            val response = call.body() as CharactersResponse

            if (response?.status == "Ok") {
                Observable.fromIterable(response.data.result).map { character: Character? ->
                    if (character?.description == "") {
                        character?.description = "No Description"
                    }
                    character?.image = character?.thumbnail?.path+"."+character?.thumbnail?.extension
                    character?.let { list.add(it) }
                }.subscribe()

            }

        } catch (e: Exception) {
        }
        return list
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.CHARACTERS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}