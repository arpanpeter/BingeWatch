package com.example.bingewatch.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bingewatch.R
import com.example.bingewatch.models.Movie
import com.example.bingewatch.ui.DetailsActivity
import com.example.bingewatch.viewHolders.EvenViewHolder
import com.example.bingewatch.viewHolders.SingleViewHolder

class MovieAdapter(private var movies: List<Movie>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var context: Context

    companion object {
        private const val VIEW_TYPE_SINGLE = 0
        private const val VIEW_TYPE_EVEN = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return when (viewType) {
            VIEW_TYPE_SINGLE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.odd_movies, parent, false)
                SingleViewHolder(view)
            }
            VIEW_TYPE_EVEN -> {
                val view = LayoutInflater.from(context).inflate(R.layout.even_layout, parent, false)
                EvenViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SingleViewHolder -> {
                holder.bind(movies[position])
                holder.itemView.setOnClickListener {
                    navigateToDetails(movies[position])
                }
            }
            is EvenViewHolder -> {
                holder.bind(movies[position])
                holder.itemView.setOnClickListener {
                    navigateToDetails(movies[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) VIEW_TYPE_SINGLE else VIEW_TYPE_EVEN
    }

    private fun navigateToDetails(movie: Movie) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("MOVIE_ID", movie.id)
        intent.putExtra("MOVIE_TITLE", movie.title)
        intent.putExtra("MOVIE_RATING", movie.rating)
        intent.putExtra("MOVIE_DESC", movie.overview)
        intent.putExtra("MOVIE_RELEASE_DATE", movie.releaseDate)
        intent.putExtra("MOVIE_POSTER", movie.posterPath)
        intent.putExtra("MOVIE_BACKDROP", movie.backdropPath)
        context.startActivity(intent)
    }

    fun setData(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}

