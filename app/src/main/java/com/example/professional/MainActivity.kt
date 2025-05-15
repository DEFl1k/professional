package com.example.professional

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as? NavHostFragment

        if (navHostFragment != null) {
            val navController: NavController = navHostFragment.navController

            val bottomNavView = findViewById<BottomNavigationView>(R.id.nav_view)
            bottomNavView.setupWithNavController(navController)
        } else {
            throw IllegalStateException("NavHostFragment not found")
        }
    }
}