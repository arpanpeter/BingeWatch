package com.example.bingewatch.repository
import com.example.bingewatch.api.RetrofitInstance
import com.example.bingewatch.models.Cast
import com.example.bingewatch.models.MovieCreditsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieCreditsRepository {
    fun getMovieCredits(movieId: Long, onSuccess: (List<Cast>) -> Unit, onFailure: (String) -> Unit) {
        RetrofitInstance.api.getMovieCredits(movieId).enqueue(object : Callback<MovieCreditsResponse> {
            override fun onResponse(call: Call<MovieCreditsResponse>, response: Response<MovieCreditsResponse>) {
                if (response.isSuccessful) {
                    val castList = response.body()?.cast ?: emptyList()
                    onSuccess(castList)
                } else {
                    onFailure("Failed to fetch movie credits")
                }
            }

            override fun onFailure(call: Call<MovieCreditsResponse>, t: Throwable) {
                onFailure(t.message ?: "ERROR")
            }
        })
    }
}
