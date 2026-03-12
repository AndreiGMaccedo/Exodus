# Exodus App

Exodus is an Android project designed to demonstrate and compare different implementation approaches between **Vanilla Android (Views/Activities)** and **Jetpack Compose**.

The project serves as a learning resource for developers looking to understand how common features like media playback are handled in both the traditional View-based system and the modern declarative UI framework.

## Project Structure

The project is divided into two main modules:

-   **`:app`**: The primary mobile application module.
-   **`:exodustv`**: A dedicated module for Android TV implementation.

## Key Features & Comparisons

### Media Playback (ExoPlayer/Media3)

One of the core demonstrations in this project is the integration of `ExoPlayer` (via Media3) in two different ways:

#### 1. Traditional Approach (`playertraditional`)
-   Located in `com.emberstudio.exodus.playertraditional`.
-   Uses `PlayerActivity` which inherits from `AppCompatActivity`.
-   UI is defined in XML (`activity_player.xml`) using `PlayerView`.
-   Handles System UI visibility (immersive mode) using `WindowInsetsController`.
-   Manages the Activity lifecycle to start and release the player.

#### 2. Compose Approach (`playercompose`)
-   Located in `com.emberstudio.exodus.playercompose`.
-   Uses `PlayerScreen` composable function.
-   Integrates the traditional `PlayerView` using `AndroidView` interop.
-   Uses `PlayerViewModel` (Hilt-injected) to manage state and logic.
-   Demonstrates custom controller implementation using Compose (`PlayerControllerView`).
-   Manages player lifecycle using Compose effects like `LaunchedEffect` and `remember`.

## Technologies Used

-   **Jetpack Compose**: Modern UI toolkit.
-   **Media3 ExoPlayer**: For video and audio streaming.
-   **Hilt**: Dependency injection.
-   **Navigation Compose**: Type-safe navigation between screens.
-   **Kotlin Coroutines & Flow**: For asynchronous operations.
-   **View Binding**: For the traditional View-based parts of the app.

## Getting Started

1.  Clone the repository.
2.  Open the project in Android Studio.
3.  Sync Gradle (the project uses Kotlin DSL `build.gradle.kts` and Version Catalogs).
4.  Run the `:app` module on an emulator or physical device.
