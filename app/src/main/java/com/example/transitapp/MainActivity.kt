package com.example.transitapp

import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.transitapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.net.URL




class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //
        // network op on same thread
        //



        //nav view
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //get location from intent start activity


        //
        //Retrofit Api
        //

        //get location from intent start activity
        val intent = intent
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)

        val location = "$latitude,$longitude"

        Log.i("Test", location);

    }
}




// PRIVATE TOKEN: sk.eyJ1IjoiYmFnaGV0dGkiLCJhIjoiY2xwMWZiYzBsMGh0ZjJrczE3YnhrN3lrcyJ9.j-cfePMLZMjz3G-_YX2ImA
//default public token: pk.eyJ1IjoiYmFnaGV0dGkiLCJhIjoiY2xwMWQzMTVvMGpmdTJrdDJ4aTBsMmpheSJ9.SfB32NAO_611Kt6TWgo5zA