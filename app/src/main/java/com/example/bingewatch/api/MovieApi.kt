package com.example.bingewatch.api

import com.example.bingewatch.models.GetMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "fdfa7cfbe0d23e74ba890a34895f93ff",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}