package com.example.bingewatch.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.example.bingewatch.R
import com.example.bingewatch.ViewModel.SearchViewModel
import com.example.bingewatch.adapters.SearchAdapter

class SearchFragment : Fragment() {

    private lateinit var searchAdapter: SearchAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var logoImageView: ImageView



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView = view.findViewById(R.id.recyclerSearch)
        logoImageView = view.findViewById(R.id.logo)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        searchAdapter = SearchAdapter(emptyList(),requireContext())
        recyclerView.adapter = searchAdapter

        // Initialize SearchViewModel
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        setupSearchView(view)

        return view
    }

    private fun setupSearchView(view: View) {
        val searchView = view.findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                newText?.let { query ->
                    if (query.length >= 3) {
                        searchViewModel.searchMovies(query)

                    } else {
                        searchAdapter.updateData(emptyList())
                    }
                }
                return true
            }
        })

        searchViewModel.searchResults.observe(viewLifecycleOwner) { movies ->
            // Update RecyclerView adapter with search results
            searchAdapter.updateData(movies)
        }
    }
}
