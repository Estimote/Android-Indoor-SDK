package com.estimote.indoorapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.estimote.indoorsdk_module.cloud.Location

/**
 * This is a simple activity to display all locations in list view.
 * You can modify it freely :)
 */
class LocationListActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: LocationListAdapter
    private lateinit var mNoLocationsView: TextView
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_list)
        mNoLocationsView = findViewById(R.id.no_locations_view) as TextView
        mRecyclerView = findViewById(R.id.my_recycler_view) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = mLayoutManager
        mAdapter = LocationListAdapter(emptyList<Location>())
        mRecyclerView.adapter = (mAdapter)
        mAdapter.setOnClickListener { locationId ->
            startActivity(MainActivity.createIntent(this, locationId))
        }
    }

    override fun onStart() {
        super.onStart()
        val locations = (application as IndoorApplication).locationsById.values.toList()
        if (locations.isEmpty()) {
            mNoLocationsView.visibility = View.VISIBLE
            mRecyclerView.visibility = View.GONE
        } else {
            mAdapter.setLocations((application as IndoorApplication).locationsById.values.toList())
        }
    }
}
