package com.example.bingewatch.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.newsprojectpractice.R

//const val MOVIE_BACKDROP = "extra_movie_backdrop"
//const val MOVIE_POSTER = "extra_movie_poster"
////const val MOVIE_TITLE = "extra_movie_title"
//const val MOVIE_RATING = "extra_movie_rating"
//const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
//const val MOVIE_OVERVIEW = "extra_movie_overview"

class DetailsActivity : AppCompatActivity() {
    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        rating = findViewById<RatingBar>(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)
        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }
    }

    private fun populateDetails(extras: Bundle) {
        extras.getString("MOVIE_BACKDROP")?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500$backdropPath")
                .transform(CenterCrop())
                .into(backdrop)
        }
        extras.getString("MOVIE_POSTER")?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }
        title.text = extras.getString("MOVIE_TITLE", "")
        rating.rating = extras.getFloat("MOVIE_RATING", 0f)
        releaseDate.text = extras.getString("MOVIE_RELEASE_DATE", "")
        overview.text=extras.getString("MOVIE_DESC","")

    }
}