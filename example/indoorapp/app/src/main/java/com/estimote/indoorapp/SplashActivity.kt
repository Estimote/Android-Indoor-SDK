package com.estimote.indoorapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.Toast
import com.estimote.coresdk.cloud.api.CloudCallback
import com.estimote.coresdk.common.config.EstimoteSDK
import com.estimote.coresdk.common.exception.EstimoteCloudException
import com.estimote.indoorsdk.cloud.IndoorCloudManagerFactory
import com.estimote.indoorsdk.cloud.Location
import io.reactivex.android.schedulers.AndroidSchedulers


/**
 * Simple splash screen to load the data from cloud.
 * Make sure to initialize EstimoteSDK with your APP ID and APP TOKEN in {@link IndoorApplication} class.
 * You can get those credentials from your Estimote Cloud account :)
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make actionbar invisible.
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)

        // Create object for communicationg with Estimote cloud
        val cloudManager = IndoorCloudManagerFactory().create(applicationContext)

        // Launch request for all locations connected to your account.
        // If you don't see any - check your cloud account - maybe you should create those locations first?
        cloudManager.getAllLocations(object : CloudCallback<List<Location>> {
            override fun success(locations: List<Location>?) {
                // Take location objects and map them to their identifiers
                val locationIds = locations?.associateBy { it.identifier }

                // save mapped locations to global pseudo "storage". You can do this in many various way :)
                if (locationIds != null) (application as IndoorApplication).locationsById.putAll(locationIds)

                // If all is fine, go ahead and launch activity with list of your locations :)
                startMainActivity()
            }

            override fun failure(serverException: EstimoteCloudException?) {
                // For the sake of this demo, you need to make sure you have an internet connection and AppID/AppToken set :)
                Toast.makeText(this@SplashActivity, "Unable to fetch location data from cloud. " +
                        "Check your internet connection and make sure you initialised our SDK with your AppId/AppToken", Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun startMainActivity(){
        startActivity(Intent(this, LocationListActivity::class.java))
    }

}
