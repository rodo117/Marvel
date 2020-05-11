package com.comics.marvel.data.youtubeapi

import com.google.gson.annotations.SerializedName

data class VideoID(@SerializedName("kind") var kind: String,@SerializedName("videoId") var videoId: String) {
}