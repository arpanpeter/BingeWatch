package com.example.bingewatch.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bingewatch.adapters.MovieAdapter
import com.example.bingewatch.ViewModel.MoviesViewModel
import com.example.bingewatch.models.Movie
import com.example.bingewatch.ui.DetailsActivity
//import com.example.bingewatch.ui.MOVIE_BACKDROP
//import com.example.bingewatch.ui.MOVIE_OVERVIEW
//import com.example.bingewatch.ui.MOVIE_POSTER
//import com.example.bingewatch.ui.MOVIE_RATING
//import com.example.bingewatch.ui.MOVIE_RELEASE_DATE
//import com.example.bingewatch.ui.MOVIE_TITLE
import com.example.newsprojectpractice.R

class MoviesFragment : Fragment() {

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclermovies)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = MovieAdapter(emptyList())
        recyclerView.adapter = adapter

        viewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            adapter.setData(movies ?: emptyList())
        })

        viewModel.getPopularMovies(1)

        return view
    }
    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(requireContext(), DetailsActivity::class.java)
        intent.putExtra("MOVIE_BACKDROP", movie.backdropPath)
        intent.putExtra("MOVIE_POSTER", movie.posterPath)
        intent.putExtra("MOVIE_TITLE", movie.title)
        intent.putExtra("MOVIE_RATING", movie.rating)
        intent.putExtra("MOVIE_RELEASE_DATE", movie.releaseDate)
        intent.putExtra("MOVIE_OVERVIEW", movie.overview)
        startActivity(intent)
    }

}
