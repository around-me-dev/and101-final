package com.example.aroundme

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import android.location.Geocoder
import android.util.Log
import com.example.aroundme.model.Event
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale

class MapsFragment(private var events: MutableList<Event>) : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var tvGpsLocation: TextView
    private val locationPermissionCode = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        tvGpsLocation = view.findViewById(R.id.gpsLocationText)

        setupMap()
        (activity as MainActivity).showLoader(false)
//        showEvents(events)
        return view
    }

    fun findLatLandFromAddress(address: String): LatLng {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses = geocoder.getFromLocationName(address, 1)
        if (addresses != null) {
            if (addresses.isNotEmpty()) {
                val address = addresses[0]
                return LatLng(address.latitude, address.longitude)
            }
        }
        return LatLng(37.4219983, -122.084)
    }
    fun showEvents(events: MutableList<Event>) {
        for (event in events) {
            val latLng = findLatLandFromAddress(event.address[0])
            val snippet = "${event.address}\n${event.date.start_date}\n${event.date.`when`}\n${event.description}"
            val markerOptions = MarkerOptions()
                .position(latLng)
                .title(event.title)
                .snippet(snippet)
            val marker = map.addMarker(markerOptions)
            marker?.tag = event
        }
    }

    private fun setupMap() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        googleMap.uiSettings.isZoomControlsEnabled = true
        getLocation()
        showEvents(events)


        googleMap.setInfoWindowAdapter(InfoAdapter(requireContext()))

    }


    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    if (location != null) {
                        val latLng = LatLng(location.latitude, location.longitude)
                        (activity as MainActivity).setCurrentLocation(latLng)
                    }
                    moveCameraToLocation(it)
                }
            }
        } else {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
    }

    private fun moveCameraToLocation(location: Location) {
        val userCoordinates = LatLng(location.latitude, location.longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(userCoordinates, 15f))
        tvGpsLocation.text = "Lat: ${location.latitude}, Long: ${location.longitude}"

        // Perform reverse geocoding to get city name
        try {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val address = addresses[0]
                    val city = address.locality
                    (activity as MainActivity).setCurrentCity(city)
                    tvGpsLocation.text = "Lat: ${location.latitude}, Long: ${location.longitude}, City: $city"
                }
            }
        } catch (e: Exception) {
            Log.e("Geocoder", "Error retrieving city name", e)
        }
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.isMyLocationEnabled = true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation()
            getLocation()
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }
}