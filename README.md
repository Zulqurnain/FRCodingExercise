# FR Interview Android App

A modern Android application built with Jetpack Compose that demonstrates clean architecture, MVVM pattern, and best practices in Android development.

## Project Overview

This project is a demonstration of building a scalable and maintainable Android application that manages a list of items. It showcases:

- Modern Android Development with Jetpack Compose
- Clean Architecture principles
- MVVM (Model-View-ViewModel) pattern
- Dependency Injection with Hilt
- Kotlin Coroutines for asynchronous operations
- Unit Testing and UI Testing
- REST API integration with Retrofit

## For Beginners: Understanding the Project

### What Does This App Do?
This app is a simple item management system that:
1. Shows a list of items
2. Allows adding new items
3. Allows deleting existing items
4. Handles loading states and errors

### Key Concepts for Beginners

#### 1. Clean Architecture
Think of the app like a well-organized house:
- **UI Layer (Living Room)**: What users see and interact with
- **Domain Layer (House Rules)**: Business logic and rules
- **Data Layer (Storage Room)**: Where and how data is stored/retrieved

#### 2. MVVM Pattern
Like a restaurant system:
- **Model**: The recipes (data and business rules)
- **View**: The dining area (what customers see)
- **ViewModel**: The kitchen (prepares data for display)

#### 3. Dependency Injection
Like plugging in appliances:
- Instead of hardcoding dependencies, we "plug them in"
- Makes it easy to swap components (like changing a light bulb)

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/jutt/frinterview/
│   │   │   ├── di/                    # Dependency Injection
│   │   │   │   └── NetworkModule.kt   # Network dependencies
│   │   │   ├── model/                 # Data models
│   │   │   │   └── Item.kt           # Item data class
│   │   │   ├── network/              # Network related classes
│   │   │   │   ├── ApiClient.kt      # Retrofit setup
│   │   │   │   └── ApiService.kt     # API endpoints
│   │   │   ├── repository/           # Data repositories
│   │   │   │   ├── ItemRepository.kt # Repository interface
│   │   │   │   └── ItemRepositoryImpl.kt # Repository implementation
│   │   │   ├── ui/                   # UI components
│   │   │   │   ├── ItemListScreen.kt # Main list screen
│   │   │   │   └── theme/            # App theming
│   │   │   ├── viewmodel/            # ViewModels
│   │   │   │   └── ItemListViewModel.kt # Main ViewModel
│   │   │   ├── FRInterviewApp.kt     # Application class
│   │   │   └── MainActivity.kt       # Main activity
│   │   └── res/                      # Resources
│   └── test/                         # Unit tests
└── build.gradle.kts                  # App level build config
```

## Detailed Component Explanations

### 1. Data Layer
#### Item.kt
```kotlin
data class Item(
    val id: Int,
    val name: String,
    val description: String
)
```
This is like a form that defines what information we store about each item.

#### ItemRepository
Think of this as a librarian:
- Knows how to get books (items)
- Knows how to add new books
- Knows how to remove books
- Doesn't care if books are digital or physical (data source abstraction)

### 2. Network Layer
#### ApiClient and ApiService
Like a telephone operator:
- Knows how to make calls (API requests)
- Knows who to call for what information
- Handles communication details

### 3. UI Layer
#### ItemListScreen
The actual screen users see:
- Shows loading spinner when working
- Displays error messages if something goes wrong
- Lists all items when available

#### ItemListViewModel
The brain behind the screen:
- Keeps track of what to show
- Handles user actions
- Talks to the repository for data

## How to Modify

### Adding New Features (For Beginners)

#### 1. Adding a New API Endpoint
```kotlin
// 1. Add to ApiService.kt
@GET("new-endpoint")
suspend fun getNewData(): Response<YourDataType>

// 2. Add to ItemRepository.kt
suspend fun getNewData(): YourDataType

// 3. Add to ItemRepositoryImpl.kt
override suspend fun getNewData(): YourDataType {
    val response = apiService.getNewData()
    // Handle response
}
```

#### 2. Adding a New UI Feature
```kotlin
// 1. Update ItemListUiState
data class ItemListUiState(
    // Add new state properties
    val newFeature: YourType = default
)

// 2. Add to ViewModel
fun handleNewFeature() {
    viewModelScope.launch {
        // Implement feature
    }
}

// 3. Add to UI
@Composable
fun NewFeatureUI() {
    // Implement UI
}
```

### Modifying Existing Features

#### 1. Changing Item Display
Edit `itemList()` in `ItemListScreen.kt`:
```kotlin
@Composable
private fun itemList(
    items: List<Item>,
    modifier: Modifier = Modifier,
) {
    // Modify the display logic here
}
```

#### 2. Changing Network Behavior
Edit `ApiClient.kt`:
```kotlin
object ApiClient {
    fun createApi(): Retrofit {
        // Modify network configuration here
    }
}
```

## Testing

### Unit Tests
Located in `src/test/`:
- Test individual components in isolation
- Verify business logic
- Mock dependencies

### UI Tests
Located in `src/androidTest/`:
- Test UI components
- Verify user interactions
- Test screen flows

## Common Tasks

### 1. Changing the API URL
In `ApiClient.kt`:
```kotlin
.baseUrl("https://your-new-api.com/")
```

### 2. Adding New Item Fields
1. Update `Item.kt`
2. Update API response handling
3. Update UI display

### 3. Modifying Theme
Edit files in `ui/theme/` directory:
- `Color.kt` for colors
- `Type.kt` for typography
- `Theme.kt` for theme configuration

## Developer Contact

For any questions or clarifications about this project, please contact:

**Zulqurnain Haider**  
Email: zulqurnainjj@gmail.com

## License

This project is proprietary and confidential. All rights reserved.

---

*Last updated: 2024*