package com.accedo.marvel.datasources


import com.accedo.marvel.data.Character
import androidx.paging.PageKeyedDataSource
import com.accedo.marvel.ApiService
import com.accedo.marvel.BuildConfig
import com.accedo.marvel.data.CharactersResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterDetailsDataSource(val character: Character) : PageKeyedDataSource<Int, Character>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {
        callback.onResult(getComicsRequestList(character), null, 2)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        callback.onResult(getComicsRequestList(character), params.key + 1)

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
    }


    private fun getComicsRequestList(character: Character): MutableList<Character> {
        var list: MutableList<Character> = ArrayList()

        try {
        val call = getRetrofit(character.id).create(ApiService::class.java).getComics(BuildConfig.TS, BuildConfig.API_KEY, BuildConfig.HASH).execute()

            val response = call.body() as CharactersResponse
            if (response.status == "Ok") {
                Observable.fromIterable(response.data.result).map { character: Character? ->
                    character?.image = character?.thumbnail?.path.plus("/").plus("standard_medium").plus(".").plus(character?.thumbnail?.extension)
                    character?.let { list.add(it) }
                }.subscribe()

            }
        }catch (e: Exception){
        }
        return list
    }

    private fun getRetrofit(id:String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.DETAIL_CHARACTER_BASE_URL+"$id/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}