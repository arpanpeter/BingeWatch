package com.example.bingewatch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.newsprojectpractice.R

import com.example.newsprojectpractice.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = Navigation.findNavController(this, R.id.moviesNavHostFragment)
        val bottomNavigation = binding.bottomNavigationView
        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }
}
