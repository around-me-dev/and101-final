package com.example.aroundme


import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle;
import android.util.Log
import android.view.View;
import android.view.animation.AnimationUtils
import android.widget.Button;
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.aroundme.model.Event
import com.google.android.gms.maps.model.LatLng

class MainActivity : AppCompatActivity() {

    private lateinit var currentFragment: Fragment
    private lateinit var loaderContainer: FrameLayout
    private lateinit var loaderImageView: ImageView
    private var currentLocation: LatLng? = null
    private var currentCity : String? = null
    private var events = mutableListOf<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Around Me"


        setupMenu()

        loaderContainer = findViewById(R.id.layout_loader_container) // The ID of the FrameLayout in layout_loader.xml
        loaderImageView = findViewById(R.id.loaderImageView)

        if (savedInstanceState == null) {
            currentFragment = MapsFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, currentFragment)
                .commit()
        }
    }

    fun setEvents(events: List<Event>) {
        this.events.clear()
        this.events.addAll(events)
        Log.d("121212MainActivity", "Events: $events")
    }

    fun getEvents(): List<Event> {
        return events
    }

    fun setCurrentLocation(location: LatLng) {
        currentLocation = location
    }

    fun getCurrentLocation(): LatLng? {
        return currentLocation
    }

    fun setCurrentCity(city: String) {
        currentCity = city
    }

    fun getCurrentCity(): String? {
        return currentCity
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
        if (currentFragment::class == fragment::class) return
        showLoader(true)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null) // Add the transaction to the back stack
            commit()
        }

        // Update the current fragment reference
        currentFragment = fragment
    }



    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()  // This will exit the app if no fragments are on the back stack
        }
    }

    fun showLoader(show: Boolean) {
        loaderContainer.visibility = if (show) View.VISIBLE else View.GONE
        if (show) {
            val rotation = AnimationUtils.loadAnimation(this, R.anim.rotate_loader)
            loaderImageView.startAnimation(rotation)
        } else {
            loaderImageView.clearAnimation()
        }
    }
}