package com.example.bingewatch.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.example.bingewatch.ViewModel.SearchViewModel
import com.example.bingewatch.adapters.SearchAdapter
import com.example.newsprojectpractice.R

class SearchFragment : Fragment() {

    private lateinit var searchAdapter: SearchAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        recyclerView = view.findViewById(R.id.recyclerSearch)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        searchAdapter = SearchAdapter(emptyList(),requireContext())
        recyclerView.adapter = searchAdapter

        // Initialize SearchViewModel
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        setupSearchView(view)

        return view
    }

    private fun setupSearchView(view: View) {
        val searchView = view.findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submission if needed
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { query ->
                    if (query.length >= 3) { // Adjust minimum query length as needed
                        // Perform search when query text changes
                        searchViewModel.searchMovies(query)
                    } else {
                        // Clear search results if query length is less than 3
                        searchAdapter.updateData(emptyList())
                    }
                }
                return true
            }
        })

        // Observe searchResults LiveData from SearchViewModel
        searchViewModel.searchResults.observe(viewLifecycleOwner, { movies ->
            // Update RecyclerView adapter with search results
            searchAdapter.updateData(movies)
        })
    }
}
