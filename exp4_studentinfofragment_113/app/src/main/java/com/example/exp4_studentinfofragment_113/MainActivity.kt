package com.example.exp4_studentinfofragment_113

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load Student Basic Details Fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.basicDetailsContainer, StudentBasicDetailsFragment())
            .commit()

        // Load Student Mark Details Fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.markDetailsContainer, StudentMarkDetailsFragment())
            .commit()
    }
}

