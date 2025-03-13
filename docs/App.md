# MIR Application Docs
This app is built with the [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) and currently 
targets *Android* and will have in the future an *iOS* build with desktop builds for *Linux*, *MacOS* and *Windows*.

## Main dependencies:
- JetBrains Compose
- Material 3
- KTOR with CIO Client
- Serialization (JSON)
- Datastore
- Lifecycle Viewmodel

## Objectives
- View, create and edit maps and missions on the robot
- Enqueue and run missions
- View status of the robot
- Manual control of the robot
- Access streams from the cameras (may not be technically possible due to API limitations)

## Project Structure
Being this app a Kotlin Multiplatform application, the project is structured as follows:
- Root `build.gradle.kts` containing root level dependencies and compilation plugins
- Root Folder `iosApp` contains the Swift code for the iOS build
- Folder `composeApp` containing the main application code
  - `build.gradle.kts` contains the project's dependencies and specific build configurations for each platform target
  - Folder `src`
    - Folder `androidMain` contains the platform-specific code for Android
    - Folder `desktopMain` contains the JVM desktop platform
    - Folder `iosMain` contains the platform-specific code for iOS in Kotlin



