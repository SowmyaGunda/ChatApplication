# ChatApplication
A simple Android application to demonstrate a chat window. This application follows MVVM structural design pattern.

The app has one screen, represents a recycler view shows list of previous conversation, a edit text to compose message, a send button to hit send and a reply button on the action bar to receive random replies. 

## Technologies and Frameworks
* Programming Language
    * Kotlin
* Components
    * Recycler View - RecyclerView makes it easy to efficiently display large sets of data
    * LiveData - A data holder class that follows the observer pattern, which means that it can be observed. Always holds/caches latest version of data. Notifies its observers when the data has changed. LiveData is lifecycle aware. UI components observe relevant data. LiveData automatically manages stopping and resuming observation, because it's aware of the relevant lifecycle status changes.
* Architecture
    * MVVM - MVVM separates your view (i.e. Activity's and Fragments) from your business logic.
* Persistent Database
    * Room - The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.
## Prerequisites
To build and run the app you need Android Studio(or any relevant IDE with android support) with gradle and its related needed packages installed.

