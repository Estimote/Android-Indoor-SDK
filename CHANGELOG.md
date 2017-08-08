Changelog
=====================
## 1.0.1 (Aug 8, 2017)
- Fix: Do not crash when location contains beacons with custom colors
- Cleanup SDK manifest to make it easier to use SDK in non-java-gradle android projects
- Cleanup SDK resources to make it easier to use SDK in non-java-gradle android projects

## 1.0.0 (Aug 1, 2017)
- Changed `IndoorLocationManager` creation api to builder pattern. Use `IndoorLocationManagerBuilder` for that. 
- Added `ScanningIndoorLocationManager` which does all the scanning by itself. You can construct it using `.withDefaultScanner()` builder method. Otherwise you should feed the `IndoorLocationManager` with scans from `BeaconManager` all by yourself (setting up the listeners, managing scan start/stop etc)
- Added smooth animations to position animations in `IndoorLocationView`
- Added improvements to positioning algorithms
- Added `onPositionOutsideLocation` callback to `OnPositionUpdateListener` to handle the case when the user is outside the current location.
- Added extension method `LocationPosition.distanceTo(anotherPosition: LocationPosition)`

## 0.10.0 (Jul 19, 2017)
- Added support for iBeacon packets as a source of data in `IndoorLocationManager`. The old method `onScannedBeacons` has been divided into two separate methods" `onScannedBeaconPackets` for iBeacon packets and `onScannedLocationPackets` for Estimote Location packets.

## 0.9.1 (Jul 7, 2017)
- Added dynamic location area computation which fixes the problem when location was too large to fit into preprocessing algorithm.
- Now locations with negative coordinates are displayed correctly. 
- Also locations are now properly scaled and centered in view. 

## 0.9.0 (Jun 29, 2017)
 - base functionality
