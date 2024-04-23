package com.example.bingewatch.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bingewatch.R
import com.example.bingewatch.adapters.FavouriteAdapter
import com.example.bingewatch.db.MovieDatabase
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var favouriteAdapter: FavouriteAdapter
    private lateinit var message: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourite, container, false)
        message = view.findViewById(R.id.nothing_wishlisted)
        recyclerView = view.findViewById(R.id.recyclerFavourites)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        favouriteAdapter = FavouriteAdapter(requireContext())
        recyclerView.adapter = favouriteAdapter

        attachSwipeToDelete()
        fetchMovies()

        return view
    }

    private fun fetchMovies() {
        if (!isAdded) {
            return
        }

        lifecycleScope.launch {
            val allMovies = withContext(Dispatchers.IO) {
                val movieDao = MovieDatabase.getDatabase(requireContext()).movieDao()
                movieDao.getAllMovies()
            }
            favouriteAdapter.setData(allMovies)
            toggleEmptyViewVisibility(allMovies.isEmpty())

        }
    }

    private fun toggleEmptyViewVisibility(isEmpty: Boolean) {
        message.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    private fun attachSwipeToDelete() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedMovie = favouriteAdapter.getMovieAt(position)

                try {
                    val context = requireContext()
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            val movieDao = MovieDatabase.getDatabase(context).movieDao()
                            movieDao.deleteMovieById(deletedMovie.id)
                        }
                        fetchMovies()
                    }

                    val snackbar = Snackbar.make(
                        requireView(),
                        "Movie deleted",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.setAction("Undo") {
                        lifecycleScope.launch {
                            withContext(Dispatchers.IO) {
                                val movieDao = MovieDatabase.getDatabase(context).movieDao()
                                movieDao.insertMovie(deletedMovie)
                            }
                            fetchMovies()
                        }
                    }
                    snackbar.setActionTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.purple
                        )
                    )
                    snackbar.show()
                } catch (e: IllegalStateException) {
                    return
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onResume() {
        super.onResume()
        fetchMovies()
    }


}
