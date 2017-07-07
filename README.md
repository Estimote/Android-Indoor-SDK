# Estimote Indoor SDK for Android

Estimote Indoor Location SDK allows real-time beacon-based mapping and indoor location.

Estimote Indoor Location is a sophisticated software solution that makes it incredibly easy and quick to map any location. Once done, you can use our SDK to visualize your approximate position within that space in real-time, in your own app.

Indoor Location creates a rich canvas upon which to build powerful new mobile experiences, from in-venue analytics and proximity marketing to frictionless payments and personalized shopping.

Estimote Indoor Location works exclusively with Estimote Beacons.


## 1. Installation

Add this line to your `build.gradle` file:

```gradle
dependencies {
  compile 'com.estimote:indoorsdk:0.9.1'
}
```

## 2. Initializing Estimote SDK

Initialize Estimote SDK in your Application class `onCreate()` method:

```Kotlin
//  To get your AppId and AppToken you need to create a new application in Estimote Cloud.
EstimoteSDK.initialize(applicationContext, appId, appToken)
// Optional, debug logging.
EstimoteSDK.enableDebugLogging(true)
```

## 3. Fetching your location from Estimote Cloud

Use `IndoorCloudManagerFactory` to get objects for communicating with our cloud.
You will need `Location` objects to start indoor positioning, so this step is really important.

```Kotlin
val cloudManager = IndoorCloudManagerFactory().create(applicationContext)
cloudManager.getLocation("your location id here", object : CloudCallback<Location> {
  override fun success(locations: Location?) {
    // do something with your location here. You will need it to init IndoorManager and IndoorView           
  }
  
  override fun failure(serverException: EstimoteCloudException?) {
    // handle error here             
  }
})
```

## 4. Adding indoor location view

Use `IndoorLocationView` to display your location on screen. You need to put it in your activity's XML layout file.

```xml
...
    <com.estimote.indoorsdk.view.IndoorLocationView
        android:id="@+id/indoor_view"
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        android:background="COLOR HERE/>
...
```
Then you can simply bind this view to an object in your activity:
```Kotlin
indoorLocationView = findViewById(R.id.indoor_view) as IndoorLocationView
// Don't forget to init view with your Location object!
indoorLocationView.setLocation(location)
```
## 5. Setting up indoor location manager
`IndoorLocationManager` is the object that does all the magic to provide you with a user's estimated position.
You need to initialize it with your application `Context` and—of course—`Location` objects.
```Kotlin
indoorLocationManager = IndoorLocationManager.create(applicationContext, mLocation)
```

## 6. Setting up beacon manager
In order for `IndoorLocationManager` to work properly, it needs to be fed data about the scanned beacons. And guess what? We'll use our `BeaconManager` for that!
```Kotlin
beaconManager = BeaconManager(this)
```

## 7. Connect all the parts together
So we now have separate objects for handling View drawing, position calculating, and beacon scanning. 
Now we need to connect them all together to create a magical experience for your users!
But don't worry, we are prepared for that! In most cases you should use our `EstimoteIndoorHelper` that sets up the data flow between all of the elements.
```Kotlin
// Use the helper below to quickly setup listeners between BeaconManager -> LocationManager -> LocationView
// It also configures BeaconManager scanning with the best scan times for indoor positioning.
// CUSTOMIZATION - if you want to customize this setup, feel free to do it manually.
EstimoteIndoorHelper.setupIndoorPositioning(beaconManager, indoorLocationManager, indoorLocationView)
```

## 8. Start!
Now we're ready to start positioning. Use code like this in your Activity's `onStart` method:
```Kotlin
...
 override fun onStart() {
        super.onStart()
        // BeaconManager needs to connect to the underlying Service,
        // this is why we use connect() method first.
        beaconManager.connect {
            // After BeaconManager has established connection with Service, 
            // we start Location Packet Discovery
            beaconManager.startLocationDiscovery()
            // ... and inform the LocationManager to start doing its magic :)
            indoorLocationManager.startPositioning()
        }
    }
...
```
and don't forget to stop using the `onStop` method!
```Kotlin
...
 override fun onStop() {
        super.onStop()
        // Stop discovery for Location packets
        beaconManager.stopLocationDiscovery()
        // Disconnect BeaconManager from underlying bluetooth Service
        beaconManager.disconnect()
        // ... and let LocationManager stop, too!
        indoorLocationManager.stopPositioning()
    }
...
```
## Your feedback and questions
At Estimote we're massive believers in feedback! Here are some common ways to share your thoughts with us:
  - Posting issue/question/enhancement on our [issues page](https://github.com/Estimote/Android-indoor-SDK/issues).
  - Asking our community managers on our [Estimote SDK for Android forum](https://forums.estimote.com/c/android-sdk).

## Changelog
To see what has changed in recent versions of Estimote SDK for Android, see the [CHANGELOG](CHANGELOG.md).
