package com.example.aroundme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aroundme.model.Event

class EventAdapter : RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    private var events = mutableListOf<Event>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eventImage: ImageView = view.findViewById(R.id.imageViewThumbnail)
        val eventTitle: TextView = view.findViewById(R.id.textViewTitle)
        val startDate: TextView = view.findViewById(R.id.textViewDate)
        val time: TextView = view.findViewById(R.id.textViewTime)
        val address: TextView = view.findViewById(R.id.textViewAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder.eventTitle.text = event.title
        holder.startDate.text = event.date.start_date
        holder.time.text = event.date.`when`
        holder.address.text = event.address[0]
        Glide.with(holder.eventImage.context)
            .load(event.thumbnail)
            .into(holder.eventImage)
    }

    fun updateEvents(newEvents: List<Event>) {
        events.clear()
        events.addAll(newEvents)
        notifyDataSetChanged()  // Notify any registered observers that the data set has changed.
    }
}
