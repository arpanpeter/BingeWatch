package com.example.bingewatch.api


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.bingewatch.util.Constants

object RetrofitInstance {
    val api: MovieApi by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }
}



