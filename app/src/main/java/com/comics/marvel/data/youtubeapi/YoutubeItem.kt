package com.comics.marvel.data.youtubeapi

import com.google.gson.annotations.SerializedName

data class YoutubeItem(@SerializedName("kind") var kind: String,
                       @SerializedName("etag") var etag: String,
                       @SerializedName("id") var id: VideoID,
                       @SerializedName("snippet") var snippet: Snippet) {
}