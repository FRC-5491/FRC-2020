# Changelog
All notable changes will be documented in this file
Format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).
This project **DOES NOT** adhere to semantic versioning

## [Unreleased]
 - Fixed DistanceSensor.java class
 - Autonomous with better fuctionality
 - Cameras?
 - 180 Degree turn button

## [3.0.0] - 2020-03-21

### Added
 - Autonomous code

### Changed
 - Distance sensors are read as generic analog inputs due to bugs involving the DistanceSensor class.

### Removed
 - DistanceSensor.java

### Fixed
 - Solenoids starting in the open position when they should be closed.s


## [2.0.0] - 2020-02-25

### Added
 - DistanceSensor.java
 - PressureSensor.java
 - Pneumatics control

### Changed
 - Pressure calculations
 - Controller type from XboxController to Joystick

### Removed
 - Air pressure calculation method

### Fixed
 - Bug that caused the "Circle of Death"


## [1.0.0] - 2020-02-05
### Added
 - JavaDoc comments to UpdateDiagnostics() and airPressure().

### Changed
- Renamed solenoid constructor so the solenoids get signals from the PCM at CAN address 6.
- Renamed DifferentialDrive.java to DriveBase.java.
- Renamed updateDiagVals() to updateDiagnostics().

## [Format Structure]

### Added

### Changed

### Deprecated

### Removed

### Fixed

## Security
