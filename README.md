# Estimote Indoor SDK for Android

Estimote Indoor Location SDK allows real-time beacon-based mapping and indoor location.

Estimote Indoor Location is a sophisticated software solution that makes it incredibly easy and quick to map any location. Once done, you can use our SDK to visualize your approximate position within that space in real-time, in your own app.

Indoor Location creates a rich canvas upon which to build powerful new mobile experiences, from in-venue analytics and proximity marketing to frictionless payments and personalized shopping.

Estimote Indoor Location works exclusively with Estimote Beacons.


## 1. Installation

Add this line to your `build.gradle` file:

```gradle
dependencies {
  compile 'com.estimote:indoorsdk:1.0.1'
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
// KOTLIN
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
```Java
// JAVA
IndoorCloudManager cloudManager = new IndoorCloudManagerFactory().create(this);
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
## 5. Setting up indoor location manager
`IndoorLocationManager` is the object that does all the magic to provide you with a user's estimated position.
You need to initialize it with your application `Context` and—of course—`Location` objects. Using `withDefaultScanner()` will allow it to scan for beacons by itself - no additional work for you!
```Kotlin
// KOTLIN
indoorLocationManager = IndoorLocationManagerBuilder(this, mLocation)
                .withDefaultScanner()
                .build()
```
```Java
// JAVA
ScanningIndoorLocationManager indoorLocationManager = 
    new IndoorLocationManagerBuilder(this, location)
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
## 6. Start!
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
## Documentation
[Javadoc documentation is available here](https://estimote.github.io/Android-Indoor-SDK/docs/index.html)

## Your feedback and questions
At Estimote we're massive believers in feedback! Here are some common ways to share your thoughts with us:
  - Posting issue/question/enhancement on our [issues page](https://github.com/Estimote/Android-indoor-SDK/issues).
  - Asking our community managers on our [Estimote SDK for Android forum](https://forums.estimote.com/c/android-sdk).

## Changelog
To see what has changed in recent versions of Estimote SDK for Android, see the [CHANGELOG](CHANGELOG.md).
