package com.example.bingewatch.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.bingewatch.db.MovieDatabase
import com.example.bingewatch.models.Movie
import com.example.newsprojectpractice.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsActivity : AppCompatActivity() {
    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var releaseDateTextView: TextView
    private lateinit var overviewTextView: TextView
    private lateinit var saveFab: FloatingActionButton
    private lateinit var movieDatabase: MovieDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        movieDatabase = MovieDatabase.getDatabase(this)

        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        titleTextView = findViewById(R.id.movie_title)
        ratingBar = findViewById(R.id.movie_rating)
        releaseDateTextView = findViewById(R.id.movie_release_date)
        overviewTextView = findViewById(R.id.movie_overview)
        saveFab = findViewById(R.id.fab_save_movie)

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }

        saveFab.setOnClickListener {
            saveMovieToDatabase(extras)
        }
    }

    private fun populateDetails(extras: Bundle) {
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${extras.getString("MOVIE_BACKDROP")}")
            .transform(CenterCrop())
            .into(backdrop)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${extras.getString("MOVIE_POSTER")}")
            .transform(CenterCrop())
            .into(poster)

        titleTextView.text = extras.getString("MOVIE_TITLE", "")
        ratingBar.rating = extras.getFloat("MOVIE_RATING", 0f) / 2
        releaseDateTextView.text = extras.getString("MOVIE_RELEASE_DATE", "")
        overviewTextView.text = extras.getString("MOVIE_DESC", "")
    }

    private fun saveMovieToDatabase(extras: Bundle?) {
        extras?.let {
            val movie = Movie(
                id = extras.getLong("MOVIE_ID"),
                title = extras.getString("MOVIE_TITLE", ""),
                overview = extras.getString("MOVIE_DESC", ""),
                posterPath = extras.getString("MOVIE_POSTER", ""),
                backdropPath = extras.getString("MOVIE_BACKDROP", ""),
                rating = extras.getFloat("MOVIE_RATING", 0f),
                releaseDate = extras.getString("MOVIE_RELEASE_DATE", "")
            )

            CoroutineScope(Dispatchers.IO).launch {
                movieDatabase.movieDao().insertMovie(movie)
            }

            // Notify the user that the movie has been saved
            Toast.makeText(this,"Movie Wishlisted",Toast.LENGTH_SHORT).show()
        }
    }
}
