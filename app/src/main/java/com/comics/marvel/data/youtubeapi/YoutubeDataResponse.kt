package com.comics.marvel.data.youtubeapi

import com.google.gson.annotations.SerializedName

data class YoutubeDataResponse(
    @SerializedName("kind") var kind: String,
    @SerializedName("etag") var etag: String,
    @SerializedName("nextPageToken") var nextPageToken: String,
    @SerializedName("regionCode") var regionCode:String,
    @SerializedName("items") var items: ArrayList<YoutubeItem>
)