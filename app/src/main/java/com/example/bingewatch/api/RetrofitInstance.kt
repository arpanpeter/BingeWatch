package com.example.bingewatch.api

import android.util.Log
import com.example.bingewatch.models.GetMoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

//    fun getPopularMovies(page: Int = 1) {
//        api.getPopularMovies(page = page)
//            .enqueue(object : Callback<GetMoviesResponse> {
//                override fun onResponse(
//                    call: Call<GetMoviesResponse>,
//                    response: Response<GetMoviesResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val responseBody = response.body()
//                        if (responseBody != null) {
//                            Log.d("Repository", "Movies: ${responseBody.movies}")
//                        } else {
//                            Log.d("Repository", "Response body is null")
//                        }
//                    } else {
//                        Log.d("Repository", "Failed to get response")
//                    }
//                }
//
//                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
//                    Log.e("Repository", "onFailure", t)
//                }
//            })
    }

