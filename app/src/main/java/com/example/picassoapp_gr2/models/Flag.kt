package com.example.picassoapp_gr2.models

import com.google.gson.annotations.SerializedName

data class Flag(
    @SerializedName("png")
    val imageUrl : String?,
    @SerializedName("alt")
    val description : String?,
)

data class Country(
    val flags: Flag
)
