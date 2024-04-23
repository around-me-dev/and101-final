package com.example.aroundme

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        adapter = EventAdapter()
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        // Set initial events from MainActivity if needed
        val events = (activity as MainActivity).getEvents()
        adapter.updateEvents(events)
        (activity as MainActivity).showLoader(false)

        return view
    }

    fun setEvents(events: List<Event>) {
        adapter.updateEvents(events)
    }
}
