package com.example.aroundme

data class Event(
    val title: String,
    val date: Date,
    val thumbnail: String,
    val description: String,
    val link: String
)

data class Date(
    val start_date: String,
    val `when`: String
)

