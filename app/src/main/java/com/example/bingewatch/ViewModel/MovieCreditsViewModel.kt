package com.example.bingewatch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bingewatch.models.Cast
import com.example.bingewatch.repository.MovieCreditsRepository

class MovieCreditsViewModel : ViewModel() {
    private val repository = MovieCreditsRepository()
    private val _castList = MutableLiveData<List<Cast>>()
    val castList: LiveData<List<Cast>> get() = _castList

    fun fetchMovieCredits(movieId: Long) {
        repository.getMovieCredits(movieId, onSuccess = { castList ->
            _castList.value = castList
        }, onFailure = { errorMessage ->
            // Handle error, maybe show a toast or update UI accordingly
        })
    }
}
