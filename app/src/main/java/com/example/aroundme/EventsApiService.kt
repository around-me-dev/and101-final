package com.example.aroundme.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EventsApiService {
    @GET("search.json")
    fun getEvents(
        @Query("engine") engine: String = "google_events",
        @Query("q") query: String,
        @Query("hl") language: String = "en",
        @Query("gl") country: String = "us",
        @Query("api_key") apiKey: String = "9d4a8a5a03dd2e79c6c789d1566d35460d38e1cfd825b7d90a02e9777ca002dc"
    ): Call<String>
}
