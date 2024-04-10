package com.example.bingewatch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bingewatch.models.Movie
import com.example.newsprojectpractice.R

class SearchAdapter(private var movies: List<Movie>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movies.size

    fun updateData(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.movieTitle)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.movieDescription)
        private val ratingTextView: TextView = itemView.findViewById(R.id.Rating)
        private val movieImage: ImageView = itemView.findViewById(R.id.movieImage)
        private val BASE="https://image.tmdb.org/t/p/w500/"

        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            descriptionTextView.text = movie.overview
            ratingTextView.text = "Rating: ${movie.rating}"

            Glide.with(itemView.context)
                .load(BASE+movie.posterPath)
                .placeholder(R.drawable.movie_icon) // Placeholder image resource
                .into(movieImage)
        }

    }
}
