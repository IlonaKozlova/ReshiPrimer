package com.example.reshiprimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.reshiprimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.numOne.text = "4674"
    }
}