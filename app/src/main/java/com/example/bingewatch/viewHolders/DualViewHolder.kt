package com.example.bingewatch.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bingewatch.R
import com.example.bingewatch.models.Movie
import com.example.bingewatch.util.Constants

class DualViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val movieTitle1: TextView = itemView.findViewById(R.id.movieTitle1)
    private val movieImage1: ImageView = itemView.findViewById(R.id.movieImage1)
    private val rating1: TextView = itemView.findViewById(R.id.movieRating1)
    val itemView1: View = itemView.findViewById(R.id.item1)
    val itemView2: View = itemView.findViewById(R.id.item2)

    private val movieTitle2: TextView = itemView.findViewById(R.id.movieTitle2)
    private val movieImage2: ImageView = itemView.findViewById(R.id.movieImage2)
    private val rating2: TextView = itemView.findViewById(R.id.movieRating2)

    fun bind(movie1: Movie, movie2: Movie?) {
        movieTitle1.text = movie1.title
        rating1.text = "Rating: ${"%.1f".format(movie1.rating)}"
        Glide.with(itemView.context)
            .load(Constants.IMAGE_BASE_URL + movie1.posterPath)
            .placeholder(R.drawable.movie_icon)
            .into(movieImage1)

        if (movie2 != null) {
            movieTitle2.text = movie2.title
            rating2.text = "Rating: ${"%.1f".format(movie2.rating)}"
            Glide.with(itemView.context)
                .load(Constants.IMAGE_BASE_URL + movie2.posterPath)
                .placeholder(R.drawable.movie_icon)
                .into(movieImage2)

            movieTitle2.visibility = View.VISIBLE
            movieImage2.visibility = View.VISIBLE
            rating2.visibility = View.VISIBLE
        } else {
            hideSecondMovie()
        }
    }

    private fun hideSecondMovie() {
        movieTitle2.visibility = View.INVISIBLE
        movieImage2.visibility = View.INVISIBLE
        rating2.visibility = View.INVISIBLE
    }
}
