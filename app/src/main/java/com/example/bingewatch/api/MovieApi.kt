package com.example.bingewatch.api

import com.example.bingewatch.models.GetMoviesResponse
import com.example.bingewatch.models.MovieCreditsResponse
import com.example.bingewatch.models.SearchResponse
import com.example.bingewatch.util.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String
    ): Response<SearchResponse>
    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Long,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<MovieCreditsResponse>


}