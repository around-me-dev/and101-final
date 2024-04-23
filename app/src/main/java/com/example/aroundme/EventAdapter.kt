
    package com.example.aroundme

    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.ImageView
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView
    import com.bumptech.glide.Glide
    import com.example.aroundme.model.Event

    class EventAdapter(private val events : List<Event>) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val eventImage: ImageView
            val eventTitle: TextView

            init {
                eventImage = view.findViewById(R.id.imageViewThumbnail)
                eventTitle = view.findViewById(R.id.textViewTitle)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.event_list_item, parent, false)

            return ViewHolder(view)
        }

        override fun getItemCount() = events.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            val event = events[position]
            holder.eventTitle.text = event.title
            Glide.with(holder.eventImage.context)
                .load(event.thumbnail)
                .into(holder.eventImage)
        }

    }
