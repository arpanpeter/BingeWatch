package com.example.bingewatch.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bingewatch.models.Cast
import com.example.bingewatch.repository.MovieCreditsRepository

class MovieCreditsViewModel : ViewModel() {
    private val repository = MovieCreditsRepository()
    private val _castList = MutableLiveData<List<Cast>>()
    val castList: LiveData<List<Cast>>
        get() = _castList

    fun fetchMovieCredits(movieId: Long) {
        repository.getMovieCredits(movieId, onSuccess = { castList ->
            _castList.value = castList
        }, onFailure = { errorMessage ->
        })
    }
}
