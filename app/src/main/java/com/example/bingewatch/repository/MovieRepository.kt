package com.example.bingewatch.repository

import com.example.bingewatch.api.MovieApi
import com.example.bingewatch.api.RetrofitInstance
import com.example.bingewatch.models.MoviesResponse
import com.example.bingewatch.models.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository() {

    private val api: MovieApi = RetrofitInstance.api

    fun getPopularMovies(page: Int = 1, onResponse: (List<Movie>?) -> Unit, onFailure: (Throwable) -> Unit) {
        api.getPopularMovies(page = page).enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    onResponse(responseBody?.movies)
                } else {
                    onFailure(Throwable("Failed to get response"))
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}
