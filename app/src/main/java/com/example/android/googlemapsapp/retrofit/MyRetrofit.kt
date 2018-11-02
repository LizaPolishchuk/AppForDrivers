package com.example.android.googlemapsapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyRetrofit {
    companion object {
    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://work.gofura.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiData = retrofit.create(ApiData::class.java)
    }

}