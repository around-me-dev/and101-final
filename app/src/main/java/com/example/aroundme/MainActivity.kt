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
import com.example.aroundme.api.EventsApiService
import com.example.aroundme.model.Event
import com.example.aroundme.model.EventsResponse
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

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
        loaderContainer = findViewById(R.id.layout_loader_container)
        loaderImageView = findViewById(R.id.loaderImageView)

        fetchEvents(currentCity ?: "Mountain View")

        if (savedInstanceState == null) {
            currentFragment = MapsFragment(events)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, currentFragment)
                .commit()
        }


    }

    fun fetchEvents(query: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://serpapi.com/")
            .addConverterFactory(ScalarsConverterFactory.create()) // Use ScalarsConverterFactory to handle raw JSON
            .build()

        val apiService = retrofit.create(EventsApiService::class.java)
        apiService.getEvents(query = "Events+in+${query}").enqueue(object : Callback<String> {
            override fun onResponse(call: retrofit2.Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    processData(response.body())
                } else {
                    Log.e("MainActivity", "Failed to fetch events: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                Log.e("MainActivity", "API call failed: ${t.message}")
            }
        })
        showLoader(false)
    }

    private fun processData(data: String?) {
        if (data != null) {
            try {
                val gson = Gson()
                val eventsResponse = gson.fromJson(data, EventsResponse::class.java)
                events.clear()
                events.addAll(eventsResponse.eventsResults ?: emptyList())
                updateListFragment()
            } catch (e: Exception) {
                Log.e("MainActivity", "Error parsing JSON", e)
            }
        }
    }

    private fun updateListFragment() {
        (currentFragment as? ListFragment)?.setEvents(events)
    }


    fun getEvents(): List<Event> {
        return events
    }

    fun setCurrentLocation(location: LatLng) {
        currentLocation = location
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
            changeFragment(MapsFragment(events))
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