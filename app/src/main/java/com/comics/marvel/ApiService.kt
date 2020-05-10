package com.comics.marvel


import com.comics.marvel.data.CharactersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("characters?")
    fun getCharacters(@Query("ts") ts: String, @Query("apikey") apiKey: String,
                      @Query("hash") hash: String , @Query("limit") limit: Int,
                      @Query("offset") offset: Int ): Call<CharactersResponse>

    @GET("comics?")
    fun getComics(@Query("ts") ts: String, @Query("apikey") apiKey: String, @Query("hash") hash: String ): Call<CharactersResponse>
}