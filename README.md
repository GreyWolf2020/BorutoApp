# Boruto Application
Boruto Android App is the front-end application for Boruto Application.
The application lists popular characters from Boruto, a famous Japanese manga.
The application is built using the Clean architecture with Jetpack Compose.

## Table of Content
- [Screenshots](#screenshots)
- [Features](#features)
- [Data](#data)
- [Installation](#installation)

## Screenshots

<p align="center">
  <img src="https://i.postimg.cc/4yX4vXCZ/Boruto.png" href="">
</p>

## Features
The application contains the following screens: splash, onboarding, searching, details and home.

### Onboarding
It appears when the application is launched for the first time.
There is a greeting section with a welcome message, 
an explorer section to encourage users to learn more about the characters,
power section to encourage users to learn how characters' powers scale against each other.
[Accompanist](https://google.github.io/accompanist/) library is used
to place the sections beside each other and to swipe from one section to the next.

### Splash
It is the first screen that appears when the application is launched with a spinning shiruken 🌀.

### Home
The screen has a list of popular 🚀 characters from Boruto.
The list item has an image, name and short description of the character.

### Search
Clicking the search button navigates to the search 🔎 screen.
It shows a list of characters from Boruto with names that contain the searched word.
The list item has an image, name and short description of the character.

### Details
Selecting a character navigates to its details.
It shows the description of the selected character.
There is an image,a description, a clan, abilities, nature types and power scale of the selected character.
The composables in the details screen takes the prominent colors of the Boruto character's image extracted using the [Palette API](https://developer.android.com/reference/kotlin/androidx/palette/graphics/Palette).

## Data
The characters' information is fetched from the [remote server](https://github.com/GreyWolf2020/com.example.borutoserver)
using the [Retrofit library](https://square.github.io/retrofit/) and the remote data is modelled in the Domain in [ApiResponse](app/src/main/java/com/example/borutoapp/domain/model/ApiResponse.kt).
The characters' information is cached locally in an SQLite database using [Room](https://developer.android.com/reference/kotlin/androidx/room/Room) library.
The locally cached information is modelled in the Domain in [Hero](app/src/main/java/com/example/borutoapp/domain/model/Hero.kt).
Imagery🎨 is loaded using [Coil](https://coil-kt.github.io/coil/) library.

## Installation
1. Install and run the [Boruto Server.](https://github.com/GreyWolf2020/com.example.borutoserver.git)
2. Setup a mobile hotspot on the android device where the android application will be installed and connect the server computer to the mobile hotspot.
3. Get the IP address of the server computer from the list of connected devices in Mobile Hotspot Settings of the android device.
4. Clone the android application from the [repository.](https://github.com/GreyWolf2020/BorutoApp.git)
5. Change the IP address of the IP_ADDRESS constant variable in [Constants.](app/src/main/java/com/example/borutoapp/util/Constants.kt):
```Kotlin
object Constants {
    const val IP_ADDRESS = "ipaddress" // assign the IP Address to IP_ADDRESS constant variable, the variable of String type.

    const val BASE_URL = "http://${IP_ADDRESS}:8080"
}
```
6. Build the application:
```bash
    ./gradlew build
```
7. Install the application on the android devices.