# NY-Times-Best-Sellers

## Building the app
The app builds using [Gradle](http://www.gradle.org/). An executable wrapper for Gradle is provided within the codebase (`gradlew` and `gradlew.bat`), so you don't need to install Gradle locally to build the app.

To clean build:

   `./gradlew clean assembledevDebug`


To build and install the debug version on the connected device just invoke the gradle wrapper script from the root project folder:

    ./gradlew clean installdevDebug


## Project Structure
The project is organized using MVVM pattern. The project contain One main modules:

- **`app`** all the Android related code resides here
    - `src/main/java` code shared between all the build variants
    - `src/main/res` contains the assets packaged within the app
    - `src/test/java` unit tests for the code in the `app/src/main`

## Build Types
currently app has 2 build types:

- **`debug`** Debuggable variant
- **`rebug`** Non-debuggable variant


## Code Structure
The main data models used are:
- **`ApiModel`**: Which is POJO class with similar keys and structure as backend API JSON response.
    - Moshi is responsible for converting JSON data to ApiModels.
    - Eg: `ApiCategories.kt`
- **`ViewStateModel`**: Which has all data needed to display the view in the screen.
    - It holds the final data values
    - It hold information like color, text with proper properties and visibility information of the views.
    - it avoid business logic, so we can make it simple to use when binding each time.
    - We use custom kotlin converter classes for this conversion. Eg: `BestSellerBooksViewStateConverter.kt`
    - Eg: `BestSellerBooksViewState.kt`


- **`**Backend.kt:`**
    - Retrofit Interface class for backend API.
    - API path will be defined here.
    - Eg: `ApiBackend.kt`
- **`**Fetcher.kt:`**
    - Fetcher responsible for initializing the retrofit client and Moshi JSON converter.
    - It hit the backend API and get the JSON response and convert to model
    - Eg: `ApiFetcher.kt`
- **`**Repository.kt:`**
    - Repository is responsible for composing the backend fetcher and convert it into ViewState in background thread.
    - It helps to manage/convert the data `BestSellerBooksViewStateConverter`.
    - Handle creating different states like loading, idle and errors.
    - Eg: `BestSellerBooksRepository.kt`
- **`**ViewModel.kt:`**
    - Responsible to present the view in mobile.
    - `Activity` communicate with `ViewModel` to initiate all the process above.
    - ViewModel would be activity lifecycle aware.
    - Eg: `BestSellerBooksViewModel.kt`

## Stack & Third Party Libraries

| Name | Version |Purpose |
|-------|-------|-------|
| [Kotlin](https://developer.android.com/kotlin) | 1.4.32 | Primary development language |
| [Android SDK Platform](https://developer.android.com/studio/releases/platforms) | 28(Android Pie) | Provides set of development and debugging tools for Android |
| [Android Build Tool](https://developer.android.com/studio/releases/build-tools.html)| 28.0.3  | Provides access to various android framework for building apps |
| [Retrofit](http://square.github.io/retrofit/) with [OkHttp3](http://square.github.io/okhttp/) | 2.2.0 | For API calls |
| [Moshi](https://github.com/square/moshi) | 1.8.0  | For JSON conversion |
| [Moshi Kotlin](https://github.com/square/moshi#kotlin)| 1.8.0  | Moshi JSON library for Kotlin |
| [RxJava](https://github.com/ReactiveX/RxJava) | 2.2.2  | For Reactive(`Observable` & `Observer`) Programming |
| [RxKotlin](https://github.com/ReactiveX/RxKotlin) | 2.1.0  | For Reactive(`Observable` & `Observer`) Programming in Kotlin |
| [Koin](https://github.com/InsertKoinIO/koin) | 2.0.0-rc-1  | For Dependency injection  |
| [Mockito](https://github.com/mockito/mockito) | 2.0.0-RC2  | For mocking framework for Java  |
| [Lifecycle-aware components](https://developer.android.com/topic/libraries/architecture/adding-components#lifecycle/) | 2.0.0-beta01  | For Lifecycle-aware components  |
| [Google Truth](https://github.com/google/truth) | 0.42  | For testing assertions  |
| [JUnit](https://github.com/junit-team/junit4) | 1.0.0  | For writing tests |
| [RoboElectric](https://github.com/robolectric/robolectric) | 4.1  | Unit testing framework for Android |

