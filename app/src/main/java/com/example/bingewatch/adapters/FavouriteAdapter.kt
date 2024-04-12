package com.example.bingewatch.adapters

import com.example.bingewatch.ui.DetailsActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bingewatch.models.Movie
import com.example.bingewatch.util.Constants
import com.example.newsprojectpractice.R

class FavouriteAdapter(private val context: Context) : RecyclerView.Adapter<FavouriteAdapter.ViewHolder>() {

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
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("MOVIE_ID", movie.id)
            intent.putExtra("MOVIE_TITLE", movie.title)
            intent.putExtra("MOVIE_RATING", movie.rating)
            intent.putExtra("MOVIE_DESC", movie.overview)
            intent.putExtra("MOVIE_RELEASE_DATE", movie.releaseDate)
            intent.putExtra("MOVIE_POSTER", movie.posterPath)
            intent.putExtra("MOVIE_BACKDROP", movie.backdropPath)
            // Pass any necessary data
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
    fun getMovieAt(position: Int): Movie {
        return movies[position]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
}
