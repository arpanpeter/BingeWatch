package com.example.bingewatch.repository

import com.example.bingewatch.api.MovieApi
import com.example.bingewatch.api.RetrofitInstance
import com.example.bingewatch.models.Cast
import com.example.bingewatch.models.GetMoviesResponse
import com.example.bingewatch.models.Movie
import com.example.bingewatch.models.MovieCreditsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository() {

    private val api: MovieApi = RetrofitInstance.api

    // Function to fetch popular movies
    fun getPopularMovies(page: Int = 1, onResponse: (List<Movie>?) -> Unit, onFailure: (Throwable) -> Unit) {
        api.getPopularMovies(page = page).enqueue(object : Callback<GetMoviesResponse> {
            override fun onResponse(call: Call<GetMoviesResponse>, response: Response<GetMoviesResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    onResponse(responseBody?.movies)
                } else {
                    onFailure(Throwable("Failed to get response"))
                }
            }

            override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }

    fun getMovieCredits(movieId: Long, onResponse: (List<Cast>) -> Unit, onFailure: (Throwable) -> Unit) {
        api.getMovieCredits(movieId = movieId).enqueue(object : Callback<MovieCreditsResponse> {
            override fun onResponse(call: Call<MovieCreditsResponse>, response: Response<MovieCreditsResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        onResponse(it.cast)
                    } ?: run {
                        onResponse(emptyList()) // Or handle null response as needed
                    }
                } else {
                    onFailure(Throwable("Failed to get response"))
                }
            }

            override fun onFailure(call: Call<MovieCreditsResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}
