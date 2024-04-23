package com.example.aroundme

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.Callback
import retrofit2.Response
import com.example.aroundme.api.EventsApiService
import com.example.aroundme.model.Event
import com.example.aroundme.model.EventsResponse
import com.google.gson.Gson

class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EventAdapter
    private var events = mutableListOf<Event>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)



        var currentEvents = (activity as MainActivity).getEvents()

        adapter = EventAdapter(events)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)



        // Fetch events in the current city
        val currentCity = (activity as MainActivity).getCurrentCity()
        fetchEvents("Events+in+${currentCity}")



        return view
    }

    private fun fetchEvents(query: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://serpapi.com/")
            .addConverterFactory(ScalarsConverterFactory.create()) // Use ScalarsConverterFactory to handle raw JSON
            .build()

        val apiService = retrofit.create(EventsApiService::class.java)
        apiService.getEvents(query = query).enqueue(object : Callback<String> {
            override fun onResponse(call: retrofit2.Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    processData(response.body())


                } else {
                    Log.e("ListFragment", "Failed to fetch events. Response: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                Log.e("ListFragment", "API call failed: ${t.message}")
            }
        })
        (activity as MainActivity).showLoader(false)

    }

    private fun processData(data: String?) {
        Log.d("ListFragment", "Processing data: $data")

        if (data != null) {
            try {
                val gson = Gson()
                val eventsResponse = gson.fromJson(data, EventsResponse::class.java)
                // Safely access the list using null-safe call
                eventsResponse.eventsResults?.forEach { event ->
                    Log.d("ListFragment", "Event Title: ${event.title}, Description: ${event.description}")
                    events.add(event)
                    adapter.notifyDataSetChanged()

                } ?: Log.w("ListFragment", "No events found in the JSON response")

                // Update the list of events in the MainActivity
                (activity as MainActivity).setEvents(eventsResponse.eventsResults ?: emptyList())


            } catch (e: Exception) {
                Log.e("ListFragment", "Error parsing JSON", e)
            }
        }
    }
}
