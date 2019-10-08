package com.accedo.marvel.datasources

import androidx.lifecycle.MutableLiveData
import com.accedo.marvel.ApiService
import com.accedo.marvel.data.Character
import com.accedo.marvel.data.CharactersResponse
import com.google.gson.JsonArray
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.paging.PageKeyedDataSource
import com.accedo.marvel.data.State

open class CharactersDataSource : PageKeyedDataSource<Int, Character>() {

    companion object {
        val API_KEY = "be08c7e14d54e9a3d0ad8b12c1e712d8"
        val HASH = "a66cf9e3611f152f02ef4e3609866681"
        val LIMIT = 10
        val TS = "1"
    }

    var state: MutableLiveData<State> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {
        callback.onResult(getCharactersRequestList(0), null, 2)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        callback.onResult(getCharactersRequestList((params.key + 1) * LIMIT), params.key + 1)

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
    }


    private fun getCharactersRequestList(offset: Int): MutableList<Character> {
        var list: MutableList<Character> = ArrayList<Character>()
        val call = getRetrofit().create(ApiService::class.java).getCharacters(
            TS,
            API_KEY,
            HASH,
            LIMIT,
            offset
        ).execute()

        val response = call.body() as CharactersResponse

        if (response?.status == "Ok") {
            val jsonArray: JsonArray = response.data.getAsJsonArray("results")

            jsonArray.forEach { jsonObject ->
                val id = jsonObject.asJsonObject.get("id").asString
                val name = jsonObject.asJsonObject.get("name").asString
                var description:String? = jsonObject.asJsonObject.get("description").asString
                println("description $description")
                if (description == "") {
                    description = "No Description"
                }
                val thumbnail = jsonObject.asJsonObject.get("thumbnail")
                val path = thumbnail.asJsonObject.get("path").asString
                val extension = thumbnail.asJsonObject.get("extension").asString

                list.add(
                    Character(
                        id,
                        name,
                        description,
                        path.plus("/").plus("landscape_large").plus(".").plus(extension)
                    )
                )
            }


        }else{
            updateState(State.ERROR)
        }
        return list
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://gateway.marvel.com/v1/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}