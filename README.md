# Estimote Indoor SDK for Android

Estimote Indoor Location SDK allows real-time beacon-based mapping and indoor location.

Estimote Indoor Location is a sophisticated software solution that makes it incredibly easy and quick to map any location. Once done, you can use our SDK to visualize your approximate position within that space in real-time, in your own app.

Indoor Location creates a rich canvas upon which to build powerful new mobile experiences, from in-venue analytics and proximity marketing to frictionless payments and personalized shopping.

Estimote Indoor Location works exclusively with Estimote Beacons.


## 1. Installation

Add this line to your `build.gradle` file:

```gradle
dependencies {
  compile 'com.estimote:indoorsdk:2.5.2'
}
```

## 2. Fetching your location from Estimote Cloud

Use `IndoorCloudManagerFactory` to get objects for communicating with our cloud.
You will need `Location` objects to start indoor positioning, so this step is really important.

```Kotlin
// KOTLIN
val cloudCredentials = EstimoteCloudCredentials("YOUR APP ID HERE", "YOUR APP TOKEN HERE")
val cloudManager = IndoorCloudManagerFactory().create(applicationContext, cloudCredentials)
        
cloudManager.getLocation("your location id here", object : CloudCallback<Location> {
  override fun success(locations: Location?) {
    // do something with your location here. You will need it to init IndoorManager and IndoorView           
  }
  
  override fun failure(serverException: EstimoteCloudException?) {
    // handle error here             
  }
})
```
```Java
// JAVA
CloudCredentials cloudCredentials = EstimoteCloudCredentials("YOUR APP ID HERE", "YOUR APP TOKEN HERE");
IndoorCloudManager cloudManager = new IndoorCloudManagerFactory().create(this, cloudCredentials);
  cloudManager.getLocation("your-location-id-here", new CloudCallback<Location>() {
    @Override
    public void success(Location location) {
      // do something with your Location object here.
      // You will need it to initialise IndoorLocationManager!
      indoorLocationView = (IndoorLocationView) findViewById(R.id.indoor_view);
      indoorLocationView.setLocation(location);
    }

    @Override
    public void failure(EstimoteCloudException e) {
      // oops!
    }
});

```

## 3. Adding indoor location view

Use `IndoorLocationView` to display your location on screen. You need to put it in your activity's XML layout file.

```xml
...
    <com.estimote.indoorsdk_module.view.IndoorLocationView
        android:id="@+id/indoor_view"
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        android:background="COLOR HERE/>
...
```
Then you can simply bind this view to an object in your activity:

```Kotlin
// KOTLIN
indoorLocationView = findViewById(R.id.indoor_view) as IndoorLocationView
// Don't forget to init view with your Location object!
indoorLocationView.setLocation(location)
```

```Java
// JAVA
IndoorLocationView indoorLocationView = (IndoorLocationView) findViewById(R.id.indoor_view);
indoorLocationView.setLocation(location);
```
## 4. Setting up indoor location manager
`IndoorLocationManager` is the object that does all the magic to provide you with a user's estimated position.
You need to initialize it with your application `Context`, your `Location` object, and your `EstimoteCloudCredentials` (We suggest declaring them in a one place and use for both `IndoorLocationCloudManager` and `IndoorLocationManager`)  Using `withDefaultScanner()` will allow to scan for beacons automatically - no additional work for you!
```Kotlin
// KOTLIN
val cloudCredentials = EstimoteCloudCredentials("YOUR APP ID HERE", "YOUR APP TOKEN HERE")
indoorLocationManager = IndoorLocationManagerBuilder(this, mLocation, cloudCredentials)
                .withDefaultScanner()
                .build()
```
```Java
// JAVA
CloudCredentials cloudCredentials = EstimoteCloudCredentials("YOUR APP ID HERE", "YOUR APP TOKEN HERE");
ScanningIndoorLocationManager indoorLocationManager = 
    new IndoorLocationManagerBuilder(this, location, cloudCredentials)
    .withDefaultScanner()
    .build();
```
And don't forget to set a listener for positioning events!

```Kotlin
// KOTLIN
indoorLocationManager.setOnPositionUpdateListener(object : OnPositionUpdateListener {
  override fun onPositionUpdate(locationPosition: LocationPosition) {
    indoorLocationView.updatePosition(locationPosition)
  }

  override fun onPositionOutsideLocation() {
    indoorLocationView.hidePosition()
  }
})
```

```Java
// JAVA
indoorLocationManager.setOnPositionUpdateListener(new OnPositionUpdateListener() {
  @Override
  public void onPositionUpdate(LocationPosition locationPosition) {
    indoorLocationView.updatePosition(locationPosition);
  }

  @Override
  public void onPositionOutsideLocation() {
    indoorLocationView.hidePosition();
  }
});
```
## 5. Start!
Now we're ready to start positioning. Use code like this in your Activity's `onStart` method:
```Kotlin
// KOTLIN
...
override fun onStart() {
  super.onStart()
  indoorLocationManager.startPositioning()
}
...
```
```Java
// JAVA
...
@Override
protected void onStart() {
  super.onStart();
  indoorLocationManager.startPositioning();
}
...
```
and don't forget to stop using the `onStop` method!
```Kotlin
// KOTLIN
...
override fun onStop() {
  super.onStop()
  indoorLocationManager.stopPositioning() 
 }
...
```
```Java
// JAVA
...
 @Override
  protected void onStop() {
    super.onStop();
    indoorLocationManager.stopPositioning();
  }
...
```
## Scanning in the background
Since version `2.0.0` it is possible to scan when the app is in the background (or even killed). For it to work, you need to display a notification to the user. This will ensure that the system won't eventually kill the scanning, and is necessary since the Android 8.0. Here is how to do this:
1. Declare an notification object like this: 
``` Kotlin
// KOTLIN
val notification = Notification.Builder(this)
              .setSmallIcon(R.drawable.notification_icon_background)
              .setContentTitle("Indoor location")
              .setContentText("Scan is running...")
              .setPriority(Notification.PRIORITY_HIGH)
              .build()
```

2. Use ` .withScannerInForegroundService(notification)` when building `IndoorLocationManager`:
``` Kotlin
// KOTLIN
val cloudCredentials = EstimoteCloudCredentials("YOUR APP ID HERE", "YOUR APP TOKEN HERE")
mIndoorLocationManager = IndoorLocationManagerBuilder(this, mLocation, cloudCredentiuals)
              .withScannerInForegroundService(notification)
              .build()
```

3. To scan while the user is not in your app (home button pressed) put `IndoorLocationManager` start/stop in `onCreate()`/`onDestroy()` of your **ACTIVITY**. 

4. To scan even after the user has killed your app (swipe) put `IndoorLocationManager` start/stop in `onCreate()`/`onDestroy()` of your **CLASS EXTENDING APPLICATION CLASS**. You will also need to handle stopping scan through the Notification, because even though the user will destroy the activity, the Notification about scanning will still remain visible. You can play with it and see the behaviour by yourself. 

## Documentation
[Javadoc documentation is available here](https://estimote.github.io/Android-Indoor-SDK/docs/index.html)

If you want to read an overview of different Indoor algorithms or a detailed description of such, please go to [iOS Readme](https://github.com/Estimote/iOS-Indoor-SDK-Source/blob/master/README.md) and [docs](https://github.com/Estimote/iOS-Indoor-SDK-Source/tree/master/docs).

## Your feedback and questions
At Estimote we're massive believers in feedback! Here are some common ways to share your thoughts with us:
  - Posting issue/question/enhancement on our [issues page](https://github.com/Estimote/Android-indoor-SDK/issues).
  - Asking our community managers on our [Estimote SDK for Android forum](https://forums.estimote.com/c/android-sdk).

## Changelog
To see what has changed in recent versions of Estimote SDK for Android, see the [CHANGELOG](CHANGELOG.md).
