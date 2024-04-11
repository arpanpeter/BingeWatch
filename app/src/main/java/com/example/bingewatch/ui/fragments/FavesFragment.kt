package com.example.bingewatch.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bingewatch.adapters.FavouriteAdapter
import com.example.bingewatch.db.MovieDatabase
import com.example.newsprojectpractice.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var favouriteAdapter: FavouriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favs, container, false)

        recyclerView = view.findViewById(R.id.recyclerFavourites)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        favouriteAdapter = FavouriteAdapter(requireContext())
        recyclerView.adapter = favouriteAdapter

        // Fetch movies from the database asynchronously
        fetchMovies()

        return view
    }

    private fun fetchMovies() {
        // Start a coroutine to perform database operations asynchronously
        lifecycleScope.launch {
            val allMovies = withContext(Dispatchers.IO) {
                // Access the database and retrieve the list of movies on IO thread
                val movieDao = MovieDatabase.getDatabase(requireContext()).movieDao()
                movieDao.getAllMovies()
            }

            // Set the retrieved list of movies to the adapter on the main thread
            favouriteAdapter.setData(allMovies)
        }
    }
}
