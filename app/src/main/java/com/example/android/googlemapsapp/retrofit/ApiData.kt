package com.example.android.googlemapsapp.retrofit

import com.example.android.googlemapsapp.data.AllData
import retrofit2.Call
import retrofit2.http.GET

interface ApiData {
    @GET("test/places?pageSize=10")
    fun getData(): Call<AllData.Data>
}