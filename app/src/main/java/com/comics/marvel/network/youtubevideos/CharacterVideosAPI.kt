package com.comics.marvel.network.youtubevideos

import com.comics.marvel.network.ApiService
import com.comics.marvel.BuildConfig
import com.comics.marvel.data.youtubeapi.Video
import com.comics.marvel.data.youtubeapi.YoutubeDataResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharacterVideosAPI {


    suspend fun getVideos(characterName:String): MutableList<Video> {
        var list: MutableList<Video> = ArrayList()

        try {
            val call = suspend {  getRetrofit().create(ApiService::class.java).getYoutubeVideos("$characterName Marvel",BuildConfig.PART, BuildConfig.MAX_RESULTS, BuildConfig.API_KEY_YOUTUBE ).execute()}

                val response = call.invoke().body() as YoutubeDataResponse
                if (response?.etag != "") {
                    Observable.fromIterable(response.items).map { item ->
                        list.add(
                            Video(
                                item.snippet.thumbnails.default.url,
                                item.snippet.title,
                                item.snippet.description
                            )
                        )
                    }.subscribe()
                }
        } catch (e: Exception) {
            println("The Exception$e")
        }
        return list
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.YOUTUBE_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}