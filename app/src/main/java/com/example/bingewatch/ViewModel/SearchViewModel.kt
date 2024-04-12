package com.example.bingewatch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bingewatch.api.RetrofitInstance
import com.example.bingewatch.models.Movie
import com.example.bingewatch.repository.SearchRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val searchRepository = SearchRepository(RetrofitInstance.api)

    private val _searchResults = MutableLiveData<List<Movie>>()
    val searchResults: LiveData<List<Movie>> = _searchResults

    fun searchMovies(query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val movies = searchRepository.searchMovies(query)
                _searchResults.postValue(movies ?: emptyList())
            } catch (e: Exception) {
                // Handle the failure case
            }
        }
    }
}