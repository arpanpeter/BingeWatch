package com.example.bingewatch.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bingewatch.models.Movie
import com.example.bingewatch.repository.MovieRepository

class MoviesViewModel : ViewModel() {

    private val repository = MovieRepository()

    private val _movies = MutableLiveData<List<Movie>?>()
    val movies: LiveData<List<Movie>?>
        get() = _movies

    fun getPopularMovies(page: Int) {
        repository.getPopularMovies(page,
            onResponse = { movies -> _movies.postValue(movies) },
            onFailure = { error -> "Failure" })
    }

}
