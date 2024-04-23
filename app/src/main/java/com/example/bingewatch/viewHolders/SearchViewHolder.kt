package com.example.bingewatch.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bingewatch.R
import com.example.bingewatch.models.Movie
import com.example.bingewatch.util.Constants

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.movieTitle)
    private val descriptionTextView: TextView = itemView.findViewById(R.id.movieDescription)
    private val ratingTextView: TextView = itemView.findViewById(R.id.rating)
    private val movieImage: ImageView = itemView.findViewById(R.id.movieImage)

    fun bind(movie: Movie) {
        titleTextView.text = movie.title
        descriptionTextView.text = movie.overview
        ratingTextView.text = "Rating: ${"%.1f".format(movie.rating / 2)}"

        Glide.with(itemView.context)
            .load(Constants.IMAGE_BASE_URL + movie.posterPath)
            .placeholder(R.drawable.movie_icon)
            .into(movieImage)
    }
}
