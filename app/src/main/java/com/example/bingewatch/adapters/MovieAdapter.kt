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
import com.example.bingewatch.R
import com.example.bingewatch.models.Movie
import com.example.bingewatch.util.Constants

class MovieAdapter(private var movies: List<Movie>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var context: Context

    companion object {
        private const val VIEW_TYPE_SINGLE = 0
        private const val VIEW_TYPE_DUAL = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return when (viewType) {
            VIEW_TYPE_SINGLE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_movies, parent, false)
                SingleViewHolder(view)
            }
            VIEW_TYPE_DUAL -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_movie_dual_row, parent, false)
                DualViewHolder(view)
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
            is DualViewHolder -> {
                val index = position * 2
                holder.bind(movies.getOrNull(index) ?: return, movies.getOrNull(index + 1))
                holder.itemView1.setOnClickListener {
                    navigateToDetails(movies[index])
                }
                holder.itemView2.setOnClickListener {
                    movies.getOrNull(index + 1)?.let { movie ->
                        navigateToDetails(movie)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) {
            VIEW_TYPE_SINGLE
        } else {
            VIEW_TYPE_DUAL
        }
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
}
//was handling duplicates in my list with this code but didn't work

//fun setData(newMovies: List<Movie>) {
//    // Filter out duplicates in the new movies list
//    val uniqueNewMovies = newMovies.distinctBy { it.id }
//
//    // Filter out movies already present in the adapter's data
//    val filteredNewMovies = uniqueNewMovies.filterNot { newMovie ->
//        movies.any { it.id == newMovie.id }
//    }
//
//    // Update the adapter's data with the filtered new movies
//    movies = movies + filteredNewMovies
//
//    // Update the set of dual view type movies
//    val newDualViewTypeMovies = filteredNewMovies.map { it.id.toInt() }.toSet()
//    dualViewTypeMovies.addAll(newDualViewTypeMovies)
//
//    notifyDataSetChanged()
//}
