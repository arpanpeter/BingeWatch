package com.example.bingewatch.ui

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.bingewatch.R
import com.example.bingewatch.adapters.CastAdapter
import com.example.bingewatch.db.MovieDatabase
import com.example.bingewatch.models.Movie
import com.example.bingewatch.util.Constants
import com.example.bingewatch.viewModel.MovieCreditsViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
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
    private lateinit var castRecyclerView: RecyclerView
    private lateinit var castAdapter: CastAdapter
    private lateinit var movieCreditsViewModel: MovieCreditsViewModel
    private var isMovieSaved: Boolean = false
    private lateinit var seeMoreText: TextView
    private lateinit var appBarLayout: AppBarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_page)
        setSupportActionBar(findViewById(R.id.toolbar))
        val backButton: ImageButton = findViewById(R.id.back_press_button)
        backButton.setOnClickListener {
            onBackPressed()
        }
        val movieTitle = intent.extras?.getString("MOVIE_TITLE")
        supportActionBar?.title = movieTitle

        val collapsingBar = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)
        collapsingBar.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white))
        appBarLayout = findViewById(R.id.appBarLayout)
        appBarLayout.setExpanded(true, true)
        movieDatabase = MovieDatabase.getDatabase(this)
        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        titleTextView = findViewById(R.id.movie_title)
        ratingBar = findViewById(R.id.movie_rating)
        releaseDateTextView = findViewById(R.id.movie_release_date)
        overviewTextView = findViewById(R.id.movie_overview)
        saveFab = findViewById(R.id.fab_save_movie)
        castRecyclerView = findViewById(R.id.castRecyclerView)
        val castHeaderTextView = findViewById<Button>(R.id.text_cast_header)
        seeMoreText = findViewById(R.id.see_more_text)
        castHeaderTextView.text = "SHOW CAST"
        castHeaderTextView.setOnClickListener {
            toggleCastListVisibility()
            toggleCastButtonText(castHeaderTextView)
        }

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)

            castAdapter = CastAdapter(emptyList())
            castRecyclerView.adapter = castAdapter
            castRecyclerView.layoutManager = GridLayoutManager(this, 3)

            movieCreditsViewModel = ViewModelProvider(this)[MovieCreditsViewModel::class.java]

            val movieId = extras.getLong("MOVIE_ID")
            isMovieSavedInDatabase(movieId)
            fetchAndDisplayCast(movieId)
            isMovieSaved = savedInstanceState?.getBoolean("IS_MOVIE_SAVED") ?: false
            updateFabColor(isMovieSaved)
        } else {
            finish()
        }

        seeMoreText.setOnClickListener {
            overviewTextView.maxLines = Integer.MAX_VALUE
            seeMoreText.visibility =
                View.GONE
        }
        saveFab.setOnClickListener {
            if (isMovieSaved) {
                deleteMovieFromDatabase(extras)
                updateFabColor(false)
            } else {
                saveMovieToDatabase(extras)
                updateFabColor(true)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("IS_MOVIE_SAVED", isMovieSaved)
        super.onSaveInstanceState(outState)
    }

    private fun toggleCastButtonText(castButton: Button) {

        if (castButton.getText().toString() == "SHOW CAST") {
            castButton.text = "HIDE CAST"
        } else {
            castButton.text = "SHOW CAST"
        }
    }


    private fun toggleCastListVisibility() {
        val recyclerView = findViewById<RecyclerView>(R.id.castRecyclerView)
        recyclerView.visibility = if (recyclerView.visibility == View.VISIBLE) {
            Log.d("DetailsActivity", "RecyclerView is being hidden")
            View.GONE
        } else {
            Log.d("DetailsActivity", "RecyclerView is being shown")
            View.VISIBLE
        }
    }


    private fun populateDetails(extras: Bundle) {
        Glide.with(this)
            .load(Constants.IMAGE_BASE_URL + extras.getString("MOVIE_BACKDROP"))
            .transform(CenterCrop())
            .placeholder(R.drawable.movie_icon)
            .into(backdrop)

        Glide.with(this)
            .load(Constants.IMAGE_BASE_URL + extras.getString("MOVIE_POSTER"))
            .transform(CenterCrop())
            .placeholder(R.drawable.movie_icon)
            .into(poster)

        titleTextView.text = extras.getString("MOVIE_TITLE", "")
        ratingBar.rating = extras.getFloat("MOVIE_RATING", 0f) / 2
        releaseDateTextView.text = extras.getString("MOVIE_RELEASE_DATE", "")
        overviewTextView.text = extras.getString("MOVIE_DESC", "")
    }

    private fun fetchAndDisplayCast(movieId: Long) {
        movieCreditsViewModel.fetchMovieCredits(movieId)
        movieCreditsViewModel.castList.observe(this) { castList ->
            castAdapter.updateData(castList)
        }
    }


    private fun isMovieSavedInDatabase(movieId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val movie = movieDatabase.movieDao().getMovieById(movieId)
            isMovieSaved = movie != null
            runOnUiThread {
                updateFabColor(isMovieSaved)
            }
        }
    }


    private fun saveMovieToDatabase(extras: Bundle?) {
        extras?.let {
            val movie = Movie(
                id = extras.getLong("MOVIE_ID", -1),
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

            isMovieSaved = true
            updateFabColor(true)
            Toast.makeText(this, "Added To WishList", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteMovieFromDatabase(extras: Bundle?) {
        extras?.let {
            val movieId = extras.getLong("MOVIE_ID", -1)
            if (movieId != -1L) {
                CoroutineScope(Dispatchers.IO).launch {
                    //deleting the movie from the database
                    movieDatabase.movieDao().deleteMovieById(movieId)
                }
                isMovieSaved = false
                updateFabColor(false)
                Toast.makeText(this, "Movie Removed from Wishlist", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateFabColor(isSaved: Boolean) {
        val color = if (isSaved) {
            ContextCompat.getColor(this, R.color.purple)
        } else {
            ContextCompat.getColor(this, R.color.yellow)
        }
        saveFab.backgroundTintList = ColorStateList.valueOf(color)
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        finish()

    }
}
