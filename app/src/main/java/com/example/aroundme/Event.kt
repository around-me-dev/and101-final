package com.example.aroundme

data class Event(
    val eventId: String,
    val title: String,
    val description: String?,
    val startTime: String,
    val endTime: String,
    val location: String?,
)
