package com.example.bingewatch.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bingewatch.R
import com.example.bingewatch.viewModel.MoviesViewModel
import com.example.bingewatch.adapters.MovieAdapter

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

        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            adapter.setData(movies ?: emptyList())
        }

        viewModel.getPopularMovies(1)

        return view
    }


}
