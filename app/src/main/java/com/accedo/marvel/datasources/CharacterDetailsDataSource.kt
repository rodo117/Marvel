package com.accedo.marvel.datasources


import com.accedo.marvel.data.Character
import androidx.paging.PageKeyedDataSource
import com.accedo.marvel.ApiService
import com.accedo.marvel.data.CharactersResponse
import com.accedo.marvel.datasources.CharactersDataSource.Companion.API_KEY
import com.accedo.marvel.datasources.CharactersDataSource.Companion.HASH
import com.accedo.marvel.datasources.CharactersDataSource.Companion.TS
import com.google.gson.JsonArray
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
        var list: MutableList<Character> = ArrayList<Character>()
        val call = getRetrofit(character.id).create(ApiService::class.java).getComics(TS, API_KEY, HASH).execute()

            val response = call.body() as CharactersResponse
            if (response.status == "Ok") {
                val jsonArray: JsonArray = response.data.getAsJsonArray("results")
                val size = jsonArray.size()
                var sub = 0
                sub = if (size >= 20) {
                    20
                } else {
                    size
                }
                jsonArray.toList().subList(0, sub).forEach { jsonObject ->
                    val id = jsonObject.asJsonObject.get("id").asString
                    val title = jsonObject.asJsonObject.get("title").asString
                    val thumbnail = jsonObject.asJsonObject.get("thumbnail")
                    val path = thumbnail.asJsonObject.get("path").asString
                    val extension = thumbnail.asJsonObject.get("extension").asString
                    val image = path.plus("/").plus("standard_medium").plus(".").plus(extension)
                    list.add(Character(id = id, name=title , image=image))
                }
            }
        return list
    }

    private fun getRetrofit(id:String): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://gateway.marvel.com/v1/public/characters/$id/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}