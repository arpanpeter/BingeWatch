package com.example.bingewatch.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.bingewatch.R

class splashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        enableEdgeToEdge()
        val splashTimeOut: Long = 3000 //
        val mainIntent = Intent(this, LoginActivity::class.java)
        Thread {
            Thread.sleep(splashTimeOut)
            startActivity(mainIntent)
            finish()
        }.start()
    }
}