package com.estimote.indoorapp

import android.app.Application
import com.estimote.coresdk.common.config.EstimoteSDK
import com.estimote.indoorsdk.cloud.Location

/**
 * START YOUR JOURNEY HERE!
 * Main app class
 */
class IndoorApplication : Application() {

    // This is map for holding all locations from your account.
    // You can move it somewhere else, but for sake of simplicity we put it in here.
    val locationsById: MutableMap<String, Location> = mutableMapOf()

    override fun onCreate() {
        super.onCreate()
        // Uncomment this line if you want to see debug logs from our SDK.
        // This may come in handy when troubleshooting problems
        // EstimoteSDK.enableDebugLogging(true)

        // !!! ULTRA IMPORTANT !!!
        // Change your credentials below to have access to locations from your account.
        // Make sure you have any locations created in cloud!
        // If you don't have your Estimote Cloud Account - go to https://cloud.estimote.com/ and create one :)
        EstimoteSDK.initialize(applicationContext, !! YOUR APP ID HERE !! ,  !! YOUR APP TOKEN HERE !!)

    }

}