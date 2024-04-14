package com.example.bingewatch.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.bingewatch.R
import com.example.bingewatch.api.RetrofitInstance
import com.example.bingewatch.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Connecting Fragments with the Bottom NavBar
        val navController = Navigation.findNavController(this, R.id.moviesNavHostFragment)
        val bottomNavigation = binding.bottomNavigationView
        NavigationUI.setupWithNavController(bottomNavigation, navController)
       // RetrofitInstance.getPopularMovies()
    }
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // Close the app when back button is pressed in HomeActivity
        finishAffinity()
    }

}
