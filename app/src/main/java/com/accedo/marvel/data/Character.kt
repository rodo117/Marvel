package com.accedo.marvel.data

import com.google.gson.JsonArray

data class Character(val id:String,val name:String, val image:String, val comics: JsonArray)