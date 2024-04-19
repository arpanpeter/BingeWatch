package com.example.bingewatch.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bingewatch.R
import com.example.bingewatch.models.Movie
import com.example.bingewatch.ui.DetailsActivity
import com.example.bingewatch.viewHolders.FavouriteViewHolder

class FavouriteAdapter(private val context: Context) : RecyclerView.Adapter<FavouriteViewHolder>() {

    private var movies: List<Movie> = listOf()

    fun setData(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.common_layout, parent, false)
        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
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
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun getMovieAt(position: Int): Movie {
        return movies[position]
    }
}
