package com.am.finalproject.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.am.finalproject.R
import com.am.finalproject.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setUpDisplayBottomNavigation()
    }

    private fun setUpDisplayBottomNavigation() {
        val navView: BottomNavigationView = binding.bottomNavigation
        val navHostController = findNavController(R.id.navHostFragmentActivityMain)
        navView.setupWithNavController(navHostController)
    }
}