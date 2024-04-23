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
import com.example.bingewatch.viewModel.SearchViewModel
import com.example.bingewatch.adapters.SearchAdapter

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

        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        setupSearchView(view)

        return view
    }

    private fun setupSearchView(view: View) {
        val searchView = view.findViewById<SearchView>(R.id.searchView)

        searchView.isIconified = false
        searchView.clearFocus()

        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                searchView.requestFocus()
            }
        }

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
            searchAdapter.updateData(movies)
        }

        searchView.setOnCloseListener {
            searchView.setQuery("", false)
            searchAdapter.updateData(emptyList())
            true
        }
    }
    override fun onResume() {
        super.onResume()
        clearSearchHistory()
    }

    private fun clearSearchHistory() {
        val searchView = view?.findViewById<SearchView>(R.id.searchView)
        searchView?.setQuery("", false)
        searchAdapter.updateData(emptyList())
    }



}
