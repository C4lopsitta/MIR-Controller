# MIR Controller
...
## Code details
This project uses the [*Kotlin Multiplatform*](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html) 
framework with build targets for Android, iOS, and Desktop. <br>
The language used is [Kotlin](https://kotlinlang.org/) but the iOS platform-specific code is written 
in Apple's Swift. Each platform has its own folder inside the `composeApp/src` path, plus a `commonMain` 
folder is present that contains Platform-Agnostic code and UI, which are shared across all five platforms. <br>
UI is managed through Kotlin's Compose framework and uses Google's Material 3 design system. <br>
The suggested IDE to use to develop the app is Android Studio as it provides all the necessary tools 
to work with both Kotlin and Android Emulators, the `Kotlin Multiplatform` plugin for Android Studio is 
also required to work properly and also it's required to have the `Enable experimental IDE Multiplatform Features` 
setting enabled under `Advanced Settings` in the IDE.

This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform]()…
