package com.example.aroundme


import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.aroundme.R
import com.example.aroundme.model.Event

class InfoAdapter(context: Context) : GoogleMap.InfoWindowAdapter {
    private val window = LayoutInflater.from(context).inflate(R.layout.info_window, null)

    private fun renderWindowText(marker: Marker, view: View) {
        val event = marker.tag as Event  // Retrieve the event object stored in tag

        view.findViewById<TextView>(R.id.title).text = event.title
        view.findViewById<TextView>(R.id.address).text = event.address[0]
        view.findViewById<TextView>(R.id.date).text = event.date.start_date
        view.findViewById<TextView>(R.id.time).text = event.date.`when`
    }

    override fun getInfoWindow(marker: Marker): View {
        renderWindowText(marker, window)
        return window
    }

    override fun getInfoContents(marker: Marker): View? {
        // Return null to use default frame with custom contents
        return null
    }
}

