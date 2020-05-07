package com.accedo.marvel.data

import com.google.gson.annotations.SerializedName

class Thumbnail(@SerializedName("path") var path: String,
                @SerializedName("extension") var extension: String)