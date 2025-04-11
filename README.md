## Anime Viewer App
**Description**
Anime Viewer is a modern Android application that showcases top anime series using the Jikan API.
Users can explore popular titles, view detailed information, and watch trailers directly in the app using a WebView. 
Built with Jetpack Compose for a clean and reactive UI experience.

## Features

**Top Anime List (Home Screen)**
Fetches and displays a list of top-rated anime from the Jikan API. Shows:
* Title
* Number of Episodes
* Rating
* Poster Image

**Anime Detail Screen**
Upon clicking an anime from the list, users can view:
* Embedded YouTube trailer via WebView (or fallback to poster if unavailable)
* Title and Plot/Synopsis
* Genre(s)
* Main Cast
* Number of Episodes
* Rating

## Tech Stack
**Language**: Kotlin  
**UI**: Jetpack Compose, Material3  
**Architecture**: MVVM (Model-View-ViewModel)  
**Dependency** Injection: Hilt  
**Networking**: Retrofit with Moshi/Converter Gson
**Media Playback**: WebView (for YouTube trailers)
**Concurrency**: Coroutines  
**Logging**: Timber  
**Image Loading**: Coil
**Navigation**: Jetpack Navigation Compose



## Project Setup

**Prerequisites**
Install Android Studio (Latest Version).
**Steps**
1. Clone the repository:    
   - git clone https://github.com/VengadeshAndroid/AnimeViewer.git
   - cd AnimeViewer
2. Open the project in Android Studio.

3. Build and run the project:
   - Use the Run button in Android Studio or execute:
     ./gradlew assembleDebug


 ## Dependencies
Your project uses the following libraries:

* Jetpack Compose
* Retrofit for API calls
* Moshi/Gson for JSON parsing
* Hilt for dependency injection
* Coil for image loading
* WebView for playing embedded trailers
* Timber for logging
* JUnit and Espresso for testing.

## Key Highlights
**Clean Architecture**: Separation of concerns with MVVM.
**Modern UI**: Jetpack Compose for reactive, declarative UI.
**Error Handling**: Handles invalid cities, network issues gracefully.

## Project Structure
```markdown
app/
│-- src/
│   ├── main/
│   │   ├── java/com.seekho.weather/
│   │   │   ├── component/        # UI components
│   │   │   ├── model/            # Data models
│   │   │   ├── navigation/       # Navigation setup
│   │   │   ├── state/            # UI State management
│   │   │   ├── theme/            # Theme and styling
│   │   │   ├── util/             # Utility classes
│   │   │   ├── view/             # UI screens
│   │   │   ├── viewmodel/        # ViewModels for each screen
│   │   │   └── webservice/       # API service classes
│   │   │   └── MainActivity.kt   # App entry point
│   │   └── res/                  # Resources (layouts, images, strings)
│   
├── test/                         # Unit tests
└── androidTest/                  # Instrumentation tests

|-- build.gradle.kts              # Gradle build script
|-- proguard-rules.pro            # Proguard configuration
|-- README.md                     # Project documentation
