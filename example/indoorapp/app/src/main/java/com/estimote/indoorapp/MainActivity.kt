package com.estimote.indoorapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.estimote.coresdk.observation.region.beacon.BeaconRegion
import com.estimote.coresdk.service.BeaconManager
import com.estimote.indoorsdk.algorithm.IndoorLocationManager
import com.estimote.indoorsdk.algorithm.IndoorLocationManagerBuilder
import com.estimote.indoorsdk.algorithm.OnPositionUpdateListener
import com.estimote.indoorsdk.algorithm.ScanningIndoorLocationManager

import com.estimote.indoorsdk.cloud.Location
import com.estimote.indoorsdk.cloud.LocationPosition
import com.estimote.indoorsdk.common.helpers.EstimoteIndoorHelper
import com.estimote.indoorsdk.view.IndoorLocationView

/**
 * Main view for indoor location
 */

class MainActivity : AppCompatActivity() {
    
    private lateinit var indoorLocationView: IndoorLocationView
    private lateinit var indoorLocationManager: ScanningIndoorLocationManager
    private lateinit var location: Location

    companion object {
        val intentKeyLocationId = "location_id"
        fun createIntent(context: Context, locationId: String) : Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(intentKeyLocationId, locationId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get location id from intent and get location object from list of locations
        setupLocation()

        // Init indoor location view here
        indoorLocationView = findViewById(R.id.indoor_view) as IndoorLocationView

        // Give location object to your view to draw it on your screen
        indoorLocationView.setLocation(location)

        // Create IndoorManager object.
        // Long story short - it takes list of scanned beacons, does the magic and returns estimated position (x,y)
        // You need to setup it with your app context and location object
        indoorLocationManager = IndoorLocationManagerBuilder(this, location)
                .withDefaultScanner()
                .build()

        // Hook the listener for position update events
        indoorLocationManager.setOnPositionUpdateListener(object: OnPositionUpdateListener {
            override fun onPositionOutsideLocation() {
                indoorLocationView.hidePosition()
            }

            override fun onPositionUpdate(locationPosition: LocationPosition) {
               indoorLocationView.updatePosition(locationPosition)
            }
        })

    }

    private fun setupLocation() {
        // get id of location to show from intent
        val locationId = intent.extras.getString(intentKeyLocationId)
        // get object of location. If something went wrong, we build empty location with no data.
        location = (application as IndoorApplication).locationsById[locationId] ?: buildEmptyLocation()
        // Set the Activity title to you location name
        title = location.name
    }

    private fun buildEmptyLocation(): Location {
        return Location("", "", true, "", 0.0, emptyList(), emptyList(), emptyList())
    }

    override fun onStart() {
        super.onStart()
        indoorLocationManager.startPositioning()
    }

    override fun onStop() {
        super.onStop()
        indoorLocationManager.stopPositioning()
    }
}
