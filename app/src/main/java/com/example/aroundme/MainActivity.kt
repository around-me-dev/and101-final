package com.example.aroundme

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity

import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import android.content.Intent;
import android.os.Bundle;
import android.util.Log
import android.view.View;
import android.widget.Button;
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Around Me"

        setupMenu()

        if (savedInstanceState == null) {
            currentFragment = MapsFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, currentFragment)
                .commit()
        }
    }

    private fun setupMenu() {
        val mapButton = findViewById<Button>(R.id.mapButton)
        val listButton = findViewById<Button>(R.id.listButton)
        val settingsButton = findViewById<Button>(R.id.settingsButton)

        mapButton.setOnClickListener {
            changeFragment(MapsFragment())
        }

        listButton.setOnClickListener {
            changeFragment(ListFragment())
        }

        settingsButton.setOnClickListener {
            changeFragment(SettingsFragment())
        }
    }

    private fun changeFragment(fragment: Fragment) {
        currentFragment = fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)  // Add this to handle back navigation through fragments
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()  // This will exit the app if no fragments are on the back stack
        }
    }
}

//
//
//class MainActivity : AppCompatActivity() {
//
////    private lateinit var map: GoogleMap
////    private lateinit var fusedLocationClient: FusedLocationProviderClient
////    private lateinit var tvGpsLocation: TextView
////    private val locationPermissionCode = 2
//
//
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        title = "Around Me"
//
//        setupMenu()
////        setupMap()
//
//
//    }
//
////    @SuppressLint("MissingPermission")
////    override fun onMapReady(googleMap: GoogleMap) {
////
////        map = googleMap
////        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
////            enableMyLocation()
////        } else {
////            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
////        }
////
////        googleMap.uiSettings.isZoomControlsEnabled = true
////        getLocation()
////    }
////
////
////
////    private fun getLocation() {
////        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
////            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
////                // Got last known location. In some rare situations this can be null.
////                location?.let {
////                    moveCameraToLocation(it)
////                }
////            }
////        } else {
////            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
////        }
////    }
////
////    private fun moveCameraToLocation(location: Location) {
////        val userCoordinates = LatLng(location.latitude, location.longitude)
////        map.moveCamera(CameraUpdateFactory.newLatLngZoom(userCoordinates, 15f))
////        tvGpsLocation.text = "Latitude: ${location.latitude}, Longitude: ${location.longitude}"
////    }
////
////    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
////        if (requestCode == locationPermissionCode) {
////            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                enableMyLocation()
////                getLocation()
////            } else {
////                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
////            }
////        }
////    }
////
////    private fun enableMyLocation() {
////        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
////            map.isMyLocationEnabled = true
////        }
////    }
//
//    private fun setupMenu() {
//        val mapButton = findViewById<Button>(R.id.mapButton)
//        val listButton = findViewById<Button>(R.id.listButton)
//        val settingsButton = findViewById<Button>(R.id.settingsButton)
//
//        mapButton.setOnClickListener {
//            Log.d("MainActivity", "Map button clicked")
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, MapsFragment())
//                .commit()
//        }
//
//        listButton.setOnClickListener {
//            Log.d("MainActivity", "List button clicked")
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, ListFragment())
//                .commit()
//        }
//
//        settingsButton.setOnClickListener {
//            Log.d("MainActivity", "Settings button clicked")
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, SettingsFragment())
//                .commit()
//        }
//
//    }
//
//
////    private fun setupMap() {
////        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
////
//////        Get the location
////        tvGpsLocation = findViewById(R.id.gpsLocationText2)
////        val button = findViewById<Button>(R.id.getLocation2)
////        button.setOnClickListener {
////            getLocation()
////        }
////
//////        Show the map
////        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment
////        mapFragment?.getMapAsync(this)
////    }
//}