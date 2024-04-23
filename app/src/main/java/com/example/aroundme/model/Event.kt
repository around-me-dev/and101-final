package com.example.aroundme.model

import com.google.gson.annotations.SerializedName

data class EventsResponse(
    @SerializedName("events_results")
    val eventsResults: List<Event>?
)

data class Event(
    val title: String,
    val date: Date,
    val address: List<String>,
    val link: String,
    val event_location_map: EventLocationMap,
    val description: String,
    val ticket_info: List<TicketInfo>,
    val venue: Venue,
    val thumbnail: String
)

data class Date(
    val start_date: String,
    val `when`: String
)

data class EventLocationMap(
    val image: String,
    val link: String,
    val serpapi_link: String
)

data class TicketInfo(
    val source: String,
    val link: String,
    val link_type: String
)

data class Venue(
    val name: String,
    val rating: Double,
    val reviews: Int,
    val link: String
)
