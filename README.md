# 📰 NewsFeed App

A modern Android News application built with Kotlin that fetches news articles from [NewsAPI.org](https://newsapi.org/), 
supports offline access using Room, background sync via WorkManager clean MVVM architecture and Jetpack best practices.

## 📱 Features

- Fetch and display latest news articles using NewsAPI
- Paginated news list with title, source, date, and thumbnail
- Article detail screen with full description, author, and image
- Local caching using Room for offline access
- Background syncing of news articles using WorkManager
- Clean MVVM architecture with Hilt DI
- Error states, retry options, loading indicators
- Kotlin Coroutines and Flows for async operations
- Safe navigation using Jetpack Navigation Component

  ## 🛠️ Tech Stack

| Layer              | Library/Technology                  |
|-------------------|-------------------------------------|
| Language          | Kotlin                              |
| Architecture      | MVVM                                |
| UI                | Jetpack Compose              |
| DI                | Hilt                                |
| Async             | Kotlin Coroutines, Flows            |
| Network           | Retrofit + OkHttp                   |
| JSON Parsing      | Gson                                |
| Image Loading     | Coil                                |
| Local Storage     | Room                                |
| Background Tasks  | WorkManager                         |
| Navigation        | Jetpack Navigation Component        |
| Pagination        | lazyColumn              |

## 🧱 Project Structure

```plaintext
NewsFeed/
├── data/                       # Data layer
│   ├── api/                    # Retrofit interfaces for network calls
│   ├── db/                     # Room database & DAO classes
│   ├── repository/             # Repository implementations
│   └── worker/                 # WorkManager background sync tasks
│
├── domain/                     # Business logic layer
│   ├── model/                  # Data models 
│   ├── usecase/                # UseCases/Interactors
│   └── repository/             # Abstract repository interfaces
│
├── di/                         # Hilt modules for dependency injection
│
├── presentation/               # UI layer
│   ├── newslist/               # News list screen components
│   └── newsdetail/             # News detail screen components
│
├── utils/                      # Utility classes, extensions, mappers
│
├── App.kt                      # Application class with @HiltAndroidApp
├── MainActivity.kt             # Hosts NavHostController
└── README.md                   # Project overview and documentation
```



## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone  https://github.com/Safana30/NewsFeed.git
cd NewsFeedApp

2. Get a NewsAPI Key
Register for a free API key at https://newsapi.org

3. Add your API key to local.properties
NEWS_API_KEY="your_api_key_here"

4.Build the Project

🔄 Background Sync
Implemented with WorkManager
Periodically fetches new articles every 4 hours
Only runs when:
Device is connected to a network

📡 Offline Support
Articles are cached locally in Room DB
When offline, the app shows cached articles
Uses Room DAO with conflict strategies for inserts



