package com.comics.marvel.data.youtubeapi

import com.google.gson.annotations.SerializedName

data class Snippet(@SerializedName("title") var title: String,
                   @SerializedName("description") var description: String,
                   @SerializedName("thumbnails") var thumbnails: Thumbnails) {
}