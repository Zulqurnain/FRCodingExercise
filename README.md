# FR Interview Android App

A modern Android application built with Jetpack Compose that demonstrates clean architecture, MVVM pattern, and best practices in Android development.

## Project Overview

This project is a demonstration of building a scalable and maintainable Android application that manages a list of items. It showcases:

* Modern Android Development with Jetpack Compose
* Clean Architecture principles
* MVVM (Model-View-ViewModel) pattern
* Dependency Injection with Hilt
* Kotlin Coroutines for asynchronous operations
* Unit Testing and UI Testing
* REST API integration with Retrofit
* Image caching and preloading
* Accessibility support
* Material Design 3 theming
* Comprehensive documentation

## Features

### 1. Modern UI Components
* Jetpack Compose-based UI
* Material Design 3 implementation
* Custom color scheme and typography
* Responsive layouts
* Dark/Light theme support
* Dynamic color support for Android 12+

### 2. Architecture & Design Patterns
* Clean Architecture implementation
* MVVM pattern with ViewModels
* Repository pattern for data management
* Dependency Injection with Hilt
* Coroutines for async operations
* State management with Compose

### 3. Performance Optimizations
* Image caching system
* Efficient list rendering
* Background processing
* Memory management
* Build optimizations

### 4. Testing
* Unit tests for business logic
* UI tests for composables
* Integration tests
* Mocking with MockK
* Test coverage reporting

### 5. Code Quality
* KDoc documentation for all components
* Lint checks and fixes
* Code style consistency
* Type safety
* Null safety

### 6. Accessibility
* Content descriptions
* Semantic properties
* Screen reader support
* Color contrast compliance
* Touch target sizing

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
│   │   │   │   ├── screens/          # Screen composables
│   │   │   │   │   ├── SplashScreen.kt
│   │   │   │   │   └── ItemListScreen.kt
│   │   │   │   └── theme/            # App theming
│   │   │   │       ├── Color.kt
│   │   │   │       ├── Type.kt
│   │   │   │       └── Theme.kt
│   │   │   ├── util/                 # Utility classes
│   │   │   │   └── ImageCache.kt     # Image caching utility
│   │   │   ├── viewmodel/            # ViewModels
│   │   │   │   └── ItemListViewModel.kt
│   │   │   ├── FRInterviewApp.kt     # Application class
│   │   │   └── MainActivity.kt       # Main activity
│   │   └── res/                      # Resources
│   └── test/                         # Unit tests
└── build.gradle.kts                  # App level build config
```

## Technical Details

### Build Configuration
* Kotlin DSL for Gradle
* Latest Android Gradle Plugin
* Kotlin 1.9.0
* Compose Compiler 1.5.0
* Hilt 2.48
* Retrofit 2.9.0
* Coroutines 1.7.3
* Material3 1.1.0

### Testing Framework
* JUnit 4
* MockK
* Compose UI Testing
* Hilt Testing

### Code Quality Tools
* Android Lint
* KtLint
* Detekt
* Compose Compiler Metrics

## Getting Started

1. Clone the repository:
```bash
git clone https://github.com/Zulqurnain/FRCodingExercise.git
```

2. Open the project in Android Studio

3. Sync the project with Gradle files

4. Run the app on an emulator or physical device

## Development Guidelines

### Code Style
* Follow Kotlin coding conventions
* Use KDoc for documentation
* Maintain consistent indentation
* Use meaningful variable names
* Keep functions focused and small

### Testing
* Write unit tests for business logic
* Include UI tests for composables
* Maintain high test coverage
* Use meaningful test names

### Documentation
* Document all public APIs
* Include usage examples
* Keep documentation up to date
* Use clear and concise language

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is proprietary and confidential. All rights reserved.

## Author

**Zulqurnain Haider**
- Email: zulqurnainjj@gmail.com
- GitHub: [@Zulqurnain](https://github.com/Zulqurnain)

## Acknowledgments

* Android Jetpack team
* Kotlin team
* Material Design team
* All contributors and reviewers