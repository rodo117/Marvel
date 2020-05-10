package com.comics.marvel.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Character(@SerializedName("id") var id: String,
                     @SerializedName("name") var name: String,
                     @SerializedName("description") var description: String = "",
                     @SerializedName("modified") var modified:String,
                     @SerializedName("thumbnail") var thumbnail: Thumbnail,
                     var image: String,
                     var isFavorite:Boolean = false):Serializable