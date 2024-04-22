package com.example.aroundme

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import okhttp3.Headers
import com.google.gson.JsonObject


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var tvGpsLocation: TextView
    private val locationPermissionCode = 2

    private lateinit var eventList: MutableList<Event>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Around Me"

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


//        Get the location
        tvGpsLocation = findViewById(R.id.gpsLocationText)
        val button = findViewById<Button>(R.id.getLocation)
        button.setOnClickListener {
            getLocation()
        }

//        Show the map
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        // Makes the api call to get list of nearby events
        val parameter = mapOf(
            "engine" to "google_events",
            "q" to "Events in Austin",
            "hl" to "en",
            "gl" to "us",
            "api_key" to "54950b2b97a4ecd17c4ff379c88d069a137a0f29d54412dead0bbd20be2cc288"
        )

        val search = getEventList(parameter)

//
//        try {
//            val results: JsonObject = search
//            val eventsResults = results.get("events_results")
//        } catch (ex: Exception) {
//            println("Exception:")
//            println(ex.toString())
//        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }

        googleMap.uiSettings.isZoomControlsEnabled = true


//        // I want to move the camera to my current location
//        val userCoordinates = LatLng(40.7749, -122.4194)
//        googleMap.uiSettings.isMyLocationButtonEnabled = true
//
//        googleMap.setOnMyLocationButtonClickListener {
//            onMyLocationButtonClick()
//        }
//
//        googleMap.setOnMyLocationClickListener {
//            onMyLocationClick(it)
//        }
//
//
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userCoordinates, 13f))

    }

    private fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG)
            .show()
    }

    private fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
            .show()
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }


    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                location?.let {
                    moveCameraToLocation(it)
                }
            }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
    }

    private fun moveCameraToLocation(location: Location) {
        val userCoordinates = LatLng(location.latitude, location.longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(userCoordinates, 15f))
        tvGpsLocation.text = "Latitude: ${location.latitude}, Longitude: ${location.longitude}"
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation()
                getLocation()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
        }
    }

    private fun getEventList(parameter: Map<String, String>) {
        val client = AsyncHttpClient()
        val json: JsonHttpResponseHandler.JSON

        client["https://serpapi.com/search.json?engine=google_events&q=Events+in+Austin&hl=en&gl=us", object: JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Event Success", "$json")

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Event Error", errorResponse)
            }
        }]

    }
}