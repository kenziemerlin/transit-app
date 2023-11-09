package com.example.transitapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY


class StartActivity : AppCompatActivity() {
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private val REQUEST_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        //
        // Set up location services
        //

        //
        // Set up location services
        //
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        getLocation()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //check to see if this is the location permission 100
        if (requestCode == REQUEST_CODE) {
            //check to see if permission granted
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            }
        }
    }

    private fun getLocation() {
        val checkPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if (checkPermission == PackageManager.PERMISSION_GRANTED) {
            //permission already granted, get location
            fusedLocationProviderClient!!.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location ->
                    if (location != null) {
                        //add intent to re-direct to main activity
                        val intent = Intent(this@StartActivity, MainActivity::class.java)
                        intent.putExtra("latitude", location.latitude)
                        intent.putExtra("longitude", location.longitude)
                        startActivity(intent)
                    }
                }
        } else {
            //ask for permission
            askPermission()
        }
    }

    private fun askPermission() {
        // Request location permission from user
        ActivityCompat.requestPermissions(
            this, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE
        )
    }


}