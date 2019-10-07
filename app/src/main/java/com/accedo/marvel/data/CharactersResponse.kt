package com.accedo.marvel.data

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class CharactersResponse (@SerializedName("status") var status:String, @SerializedName("data") var data: JsonObject)