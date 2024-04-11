package com.example.bingewatch.adapters

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
import com.example.bingewatch.ui.DetailsActivity
import com.example.bingewatch.util.Constants
import com.example.newsprojectpractice.R


class SearchAdapter(private var movies: List<Movie>,private val context: Context) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("MOVIE_ID", movie.id)
            intent.putExtra("MOVIE_TITLE",movie.title)
            intent.putExtra("MOVIE_RATING",movie.rating)
            intent.putExtra("MOVIE_DESC",movie.overview)
            intent.putExtra("MOVIE_RELEASE_DATE",movie.releaseDate)
            intent.putExtra("MOVIE_POSTER",movie.posterPath)
            intent.putExtra("MOVIE_BACKDROP",movie.backdropPath)
            // Pass any necessary data
            context.startActivity(intent)
        }
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

        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            descriptionTextView.text = movie.overview
            ratingTextView.text = "Rating: ${movie.rating}"

            Glide.with(itemView.context)
                .load(Constants.IMAGE_BASE_URL+movie.posterPath)
                .placeholder(R.drawable.movie_icon) // Placeholder image resource
                .into(movieImage)
        }

    }
}
