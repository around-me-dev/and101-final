package com.example.aroundme;

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityListFragment(val items: List<String> = listOf("row1", "row2", "row3")) : AppCompatActivity(R.layout.activity_list_fragment) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = ActivityListViewAdapter(items)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView);
        recyclerView.adapter = adapter
    }
}
