package com.example.bingewatch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bingewatch.models.Movie
import com.example.newsprojectpractice.R

class FavouriteAdapter : RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {

    private var movies: List<Movie> = listOf() // Initialize with an empty list

    fun setData(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
        private val movieDescription: TextView = itemView.findViewById(R.id.movieDescription)
        private val movieImage: ImageView = itemView.findViewById(R.id.movieImage)
        private val rating: TextView = itemView.findViewById(R.id.Rating)
        private val BASE_URL = "https://image.tmdb.org/t/p/w500/"

        fun bind(movie: Movie) {
            movieTitle.text = movie.title
            movieDescription.text = movie.overview
            rating.text = "Rating: ${movie.rating}"
            Glide.with(itemView.context)
                .load(BASE_URL + movie.posterPath)
                .placeholder(R.drawable.movie_icon)
                .into(movieImage)
        }
    }
}
