package com.example.bingewatch.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bingewatch.R
import com.example.bingewatch.models.Movie
import com.example.bingewatch.util.Constants

class SingleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
    private val movieDescription: TextView = itemView.findViewById(R.id.movieDescription)
    private val movieImage: ImageView = itemView.findViewById(R.id.movieImage)
    private val rating: TextView = itemView.findViewById(R.id.Rating)

    fun bind(movie: Movie) {
        movieTitle.text = movie.title
        movieDescription.text = movie.overview
        rating.text = "Rating: ${"%.1f".format(movie.rating)}"
        Glide.with(itemView.context)
            .load(Constants.IMAGE_BASE_URL + movie.posterPath)
            .placeholder(R.drawable.movie_icon)
            .into(movieImage)
    }
}
