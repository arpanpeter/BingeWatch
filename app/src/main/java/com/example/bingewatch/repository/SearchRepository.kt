package com.example.bingewatch.repository

import com.example.bingewatch.api.MovieApi
import com.example.bingewatch.models.Movie
import com.example.bingewatch.util.Constants


class SearchRepository(private val api: MovieApi) {

    suspend fun searchMovies(query: String): List<Movie>? {
        return try {
            val response = api.searchMovies(query, apiKey =Constants.API_KEY)
            if (response.isSuccessful) {
                val responseBody = response.body()
                responseBody?.results
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
