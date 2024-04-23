package com.example.aroundme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class EventAdapter(private val eventList: List<Event>) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventList[position]
        holder.titleTextView.text = event.title
        holder.dateTextView.text = "${event.date.start_date} - ${event.date.`when`}"
        holder.descriptionTextView.text = event.description

        // Load the thumbnail using Glide
        Glide.with(holder.itemView.context)
            .load(event.thumbnail)
            .placeholder(R.drawable.list_button_icon) // Consider adding a placeholder in your drawable resources
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = eventList.size
}
