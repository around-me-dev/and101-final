package com.example.aroundme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng



class MainActivity : AppCompatActivity(), OnMapReadyCallback {

//    private var mGoogleMap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Add a marker in Sydney and move the camera
        val userCoordinates = LatLng(37.7749, -122.4194)
        googleMap.addMarker(com.google.android.gms.maps.model.MarkerOptions().position(userCoordinates).title("Marker in San Francisco"))
        googleMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLng(userCoordinates))
    }
}