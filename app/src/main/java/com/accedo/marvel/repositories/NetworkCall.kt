package com.accedo.marvel.repositories

import com.accedo.marvel.ApiService
import com.accedo.marvel.data.Character
import com.accedo.marvel.data.CharactersResponse
import com.google.gson.JsonArray
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NetworkCall : ManageDataContract {
    private var charactersData = MutableLiveData<List<Character>>()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://gateway.marvel.com/v1/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun getCharacters(offset:Int): MutableLiveData<List<Character>> {

        println("GetCharactersCalled $offset")

        GlobalScope.launch {
            val call = getRetrofit().create(ApiService::class.java).getCharacters(
                "1",
                "be08c7e14d54e9a3d0ad8b12c1e712d8",
                "a66cf9e3611f152f02ef4e3609866681",
                10,
                offset
            ).execute()
            val response = call.body() as CharactersResponse
            var list: MutableList<Character> = ArrayList<Character>()
            if (response.status == "Ok") {
                val jsonArray: JsonArray = response.characters.getAsJsonArray("results")

                jsonArray.forEach { jsonObject ->
                    val name = jsonObject.asJsonObject.get("name").asString
                    val thumbnail = jsonObject.asJsonObject.get("thumbnail")
                    val path = thumbnail.asJsonObject.get("path").asString
                    val extension = thumbnail.asJsonObject.get("extension").asString
                    list.add(
                        Character(
                            name, path.plus("/").plus("landscape_large").plus(".").plus(extension)
                        )
                    )
                }
                withContext(Dispatchers.Main) {
                    charactersData.value = list
                }
            }
        }

        return charactersData
    }
}