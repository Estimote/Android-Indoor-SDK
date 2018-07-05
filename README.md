# Estimote Indoor SDK for Android

Estimote Indoor Location is a sophisticated software solution that makes it incredibly easy to define your position is indoor space and share it with others.
You can use our SDK to visualize approximate position of all sdk's users within mapped space in real-time, in your own app.

Indoor Location allows you to easyly integrate with world-class standard maping engines like GoogleMaps to build powerful mobile experience from in-venue analytics and proximity marketing to frictionless payments and personalized shopping.

Estimote Indoor Location works exclusively with Estimote Beacons.

## 1. Installation

Estimote Indoor Location SDK consists core indoor package and (optional) GoogleMaps rendering engine.
To include both bundles in your build, add this lines to your `build.gradle` file:

```gradle
dependencies {
  // Core Estimote Indoor Location SDK
  implementation 'com.estimote:indoorsdk:3.0.0-beta-01' 
  // Optional GoogleMaps rendering Engine
  implementation 'com.estimote:google-maps-renderer:3.0.0-beta-01' 
}
```

## 2. Initializing Estimote SDK

To initialize Estimote Indoor Location SDK, collect your applicationID and applicationToken (available on [Estimote cloud](https://cloud.estimote.com/)) and  call handy static factory method as follows:

```Kotlin
//  To get your AppId and AppToken you need to create a new application in Estimote Cloud.
 EstimoteIndoorSDK.create(applicationContext, <Your Application ID>, <Your Application Token>)
```
 __*IMPORTANT:* To gain best possible performance, you should use single instance of EstimoteIndoorSDK across your application.__

## 3. Tracking your position
Estimote Indoor Location SDK provides simple, conscise api to controll your position tracking and thus making it available for Estimote Indoor infrastructure.
Initialize position tracking as follows:

``` Kotlin
  val trackingHandler = indoorSDK
    .trackPosition()
    .where(locationNamed("A1"))
    .start()
    
    ....
    
    trackingHandler.stop()
```

What's going on here? Its pretty simple. EstimoteIndoorSDK class is you best friend here. you are starting all interaction with this class instance. To initate position tracking simply call `EstimoteIndoorSDK::trackPosition` method. This will give you posibility to configure tracking options and to start actual tracking process. Here are available posibilities:
* `where(locationSelector: (EstimoteSimpleIndoorLocation)->Boolean)` - use this method to specify location you'd like the position to be track within. Core Estimote Location package delivers two handy predicates to be used here:
  * `locationNamed(locationName: String)` - use it to specify location by its name
  * `locatonWithIdentifier(locationId: String)` - use it to specify location by its identifer
* `withErrorHandler(handler: IndoorErrorHandler)` - use it to specify callback to be called each time some error occurred during your location tracking
* `withPositionUpdateInterval(intervalMillis: Long)` - use it to specify how fequently your position should be updated
* `withOnOutsideLocation(action: () -> Unit)` - use it to specify action to be called each time you leave location the tracking was started in.
* `withScannerInForegroundService(notification: Notification)` - use it to specify notification to be used by Estimote Indoor SDK. It will be used to ensure the all indoor features will keep up and running when your application go to background
* `start()` - once everything is configured - start tracking. As a result, you'll get the `trackingHandler` instance. Use it to stop tracking process where you like.

## 4. Presenting positions
Estimote Indoor Location SDK allows you to easily present positions of all users with active tracking in place. 
In other words - once some user starts his position tracking by calling `indoorSDK.trackPosition()...` api, you are able to view his position in realtime. Pure magic. Here's how to setup it:
```Kotlin
val locationPresenter = indoorSDK
  .showPositions()
  .where(locationNamed("A1"))
  .withErrorHandler(IndoorErrorHandler { Log.d("My Fancy App", "locationPresenter error: $it") } )
  .build( GoogleMapsEstimoteIndoorRenderer(googleMap) )
  locationPresenter.start()
```
Once again - You are starting with `EstimoteIndoorSDK` instance. This time you need to choose `showPositions()` method.
This will allow you to configure presenting optons. Here's what available:
* `where(locationSelector: (EstimoteSimpleIndoorLocation)->Boolean)` - just like in case of tracking, use it to specify location you'd like to be presented.
* `withErrorHandler(handler: IndoorErrorHandler)` - use it to specify callback to be called each time some error occurred while location is presenting
* `build(estimoteIndoorRenderer: EstimoteIndoorRenderer)` - this will create actual `EstimoteIndoorLocationPresenter` instance. You'll use it to dynamically controll presenting process. As a `build` method parameter, you need to specify the Rendering engine instance to be used to render location. If you included `com.estimote:google-maps-renderer` in you build gradle dependencies, then `GoogleMapsEstimoteIndoorRenderer` can be used (more details bellow).

### Google Maps based renderering engine
We are delivering super cool Indoor location renderning engin based on Google Maps technology. It is available with `com.estimote:google-maps-renderer` package. It leverages googleMaps object to allows you to display your location together with realtime positioning of all users inside. All You need to do to use it is to simply create `GoogleMapsEstimoteIndoorRenderer` instance passing GoogleMaps object as a constructor parameter. All super cool Estimote's Indoor features will be available on yor map. 
Important thing to note is taht you have to initialise GoogleMaps on you own and pass it to Estimotes's framework.
This is because Google requires application specific keys to be setup to make maps engine woriking propperly.

## Your feedback and questions
At Estimote we're massive believers in feedback! Here are some common ways to share your thoughts with us:
  - Posting issue/question/enhancement on our [issues page](https://github.com/Estimote/Android-SDK/issues).
  - Asking our community managers on our [Estimote SDK for Android forum](https://forums.estimote.com/c/android-sdk).

## Changelog
To see what has changed in recent versions of Estimote SDK for Android, see the [CHANGELOG](CHANGELOG.md).
