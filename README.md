# 🎬 Movie Watchlist App

A modern Android application built using **Jetpack Compose** that allows users to browse movies, view details, manage a personal watchlist, and track watched parts.

---

## 🚀 Features

### 📌 Browse Movies

* Display list of movies from bundled JSON
* Movie card includes:

    * Poster (with placeholder if null)
    * Title, Year, Genre
    * Rating (or *"Not rated"*)
    * Director

### 🔍 Search & Filter

* Search movies by title
* Filter using genre chips:

    * All | Sci-Fi | Thriller | Drama | Comedy
* Combined filtering (search + chips together)

### 🎥 Movie Details

* Full movie details screen
* List of parts (if available)
* Add / Remove from Watchlist
* Mark parts as watched
* Mark movie as **Completed** when all parts are watched

### ⭐ Watchlist

* Separate tab for watchlisted movies
* Persistent data using Room
* Empty state UI when no movies added

### 📱 Responsive UI

* **Mobile (<600dp)**:

    * Bottom Navigation (Browse + Watchlist)
    * Navigation to detail screen
* **Tablet (≥600dp)**:

    * Two-pane layout (List + Detail)
    * Shared ViewModel

---

## 🏗️ Tech Stack

* **Kotlin**
* **Jetpack Compose + Material3**
* **Room Database**
* **Hilt (Dependency Injection)**
* **MVVM Architecture**
* **StateFlow + Coroutines**
* **Compose Navigation**
* **Gson / Moshi** (JSON Parsing)
* **Coil** (Image Loading)

---

## 🧠 Architecture Overview

The app follows **MVVM architecture** with a clear separation of concerns:

* **UI Layer (Compose)** → Observes StateFlow
* **ViewModel** → Manages UI state & business logic
* **Repository** → Handles data (Room + JSON)
* **Data Layer** → Room DB + JSON loader

### 📂 Project Structure

```
com.example.moviewatchlist
│
├── data
│   ├── local (Room DB, DAO, Entities)
│   ├── remote (JSON parsing)
│   └── repository
│
├── domain
│   └── model
│
├── ui
│   ├── screens
│   │   ├── list
│   │   ├── detail
│   │   └── watchlist
│   ├── components
│   └── theme
│
├── viewmodel
│
└── di (Hilt modules)
```

---

## 💾 Data Handling

* Initial data loaded from **assets JSON (first launch only)**
* Stored in **Room Database**
* All user actions persist:

    * Watchlist
    * Watched parts
    * Completion status

---

## ⚠️ Edge Cases Handled

* ✅ Null rating → *"Not rated"*
* ✅ Null duration → *"Duration unknown"*
* ✅ Null poster → Placeholder UI
* ✅ Empty parts → *"No parts listed"* + disabled interactions
* ✅ Completed movies → State persists correctly
* ✅ Empty search → *"No movies found"*
* ✅ Empty watchlist → Proper empty state UI

---

## 🛠️ How to Build & Run

1. Clone the repository:

   ```bash
   git clone <https://github.com/VivekYadavDeveloper/MovieHubNative.git>
   ```

2. Open in **Android Studio**

3. Sync Gradle

4. Run the app on:

    * Emulator OR
    * Physical device

---

## 💡 Assumptions

* JSON is bundled locally and used only for initial seeding
* Movie IDs are unique
* Parts list defines completion logic
* No backend/API integration

---

## 🔮 Future Improvements

* Add sorting (year, rating, title)
* Add animations (watchlist toggle)
* Unit testing (ViewModel logic)
* Pull-to-refresh
* Pagination / large dataset handling
* Dark mode enhancements

---

## 🎁 Bonus (if implemented)

* Watchlist badge on movie cards
* Sorting functionality
* Animations
* Unit tests for filtering logic

---

## 📌 Submission

* GitHub repository with complete code
* README (this file)
* Clean, maintainable, scalable architecture

---

## 🧑‍💻 Notes

* Built for evaluation purposes
* Focus on clean architecture and state management
* Handles null and edge cases robustly

---

⭐ If you like this project, feel free to star the repo!
