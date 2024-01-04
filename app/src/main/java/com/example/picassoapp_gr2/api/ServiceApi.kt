package com.example.picassoapp_gr2.api

import com.example.picassoapp_gr2.models.Country
import retrofit2.Call
import retrofit2.http.GET

interface ServiceApi {
    @GET("?fields=flags")
    fun getFlags(): Call<List<Country>>
}