Changelog
=====================
## 0.10.0 (Jul 19, 2017)
- Added support for iBeacon packets as a source of data in `IndoorLocationManager`. The old method `onScannedBeacons` has been divided into two separate methods" `onScannedBeaconPackets` for iBeacon packets and `onScannedLocationPackets` for Estimote Location packets.

## 0.9.1 (Jul 7, 2017)
- Added dynamic location area computation which fixes the problem when location was too large to fit into preprocessing algorithm.
- Now locations with negative coordinates are displayed correctly. 
- Also locations are now properly scaled and centered in view. 

## 0.9.0 (Jun 29, 2017)
 - base functionality
