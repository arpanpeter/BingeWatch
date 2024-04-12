package com.example.bingewatch.repository

import com.example.bingewatch.api.MovieApi
import com.example.bingewatch.models.Movie
import com.example.bingewatch.models.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchRepository(private val api: MovieApi) {

    suspend fun searchMovies(query: String): List<Movie>? {
        return try {
            val response = api.searchMovies(query, apiKey = "fdfa7cfbe0d23e74ba890a34895f93ff")
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
