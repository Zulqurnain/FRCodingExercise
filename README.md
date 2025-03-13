# FR Interview Android App

A modern Android application built with Jetpack Compose that demonstrates clean architecture, MVVM pattern, and best practices in Android development.

## Demo
[![App Demo](recording.mp4)](recording.mp4)

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
* Android Gradle Plugin 8.2.2
* Kotlin 1.9.22
* Compose Compiler 1.5.8
* Hilt 2.50
* Retrofit 2.9.0
* Coroutines 1.7.3
* Material3 1.2.0
* AndroidX Core KTX 1.12.0
* AndroidX Lifecycle 2.7.0
* AndroidX Navigation 2.7.7
* AndroidX Room 2.6.1
* AndroidX Test 1.5.0
* JUnit 4.13.2
* MockK 1.13.9
* Detekt 1.23.5
* KtLint 0.50.0

### Native Dependencies
* Android SDK 34 (Android 14)
* Minimum SDK 24 (Android 7.0)
* Target SDK 34 (Android 14)
* NDK 26.1.10909125
* CMake 3.22.1
* Gradle 8.2
* JDK 17

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
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'feat: add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Pull Request Guidelines
* Follow conventional commit messages
* Include tests for new features
* Update documentation as needed
* Ensure all tests pass
* Follow the existing code style

## Build and Run

### Prerequisites
* Android Studio Hedgehog | 2023.1.1 or newer
* JDK 17
* Android SDK 34
* Gradle 8.2

### Build Variants
* Debug: Development build with debugging tools
* Release: Production build with optimizations enabled
* DebugTest: For running tests in debug mode

### Performance Tips
* Enable Gradle build cache
* Use parallel execution
* Enable configuration cache
* Use the latest Android Studio version

## Troubleshooting

### Common Issues
1. Gradle Sync Failures
   - Clean project and rebuild
   - Invalidate caches and restart
   - Update Gradle wrapper

2. Build Errors
   - Check SDK versions
   - Verify dependency versions
   - Update Android Studio

3. Runtime Issues
   - Check logcat for errors
   - Verify permissions
   - Test on different devices

## License

This project is proprietary and confidential. All rights reserved.

## Author

**Zulqurnain Haider**
- Email: zulqurnainjj@gmail.com
- GitHub: [@Zulqurnain](https://github.com/Zulqurnain)
- LinkedIn: [Zulqurnain Haider](https://www.linkedin.com/in/zulqurnain-haider/)

## Acknowledgments

* Android Jetpack team for providing excellent libraries
* Kotlin team for the powerful language
* Material Design team for the beautiful UI components
* All contributors and reviewers
* Fetch Rewards team for the opportunity

## Support

For support, please:
1. Check the documentation
2. Review existing issues
3. Create a new issue if needed
4. Contact the maintainer

## Roadmap

### Planned Features
* [ ] Offline support
* [ ] Data persistence
* [ ] Analytics integration
* [ ] Performance monitoring
* [ ] Accessibility improvements

### Known Issues
* See GitHub Issues for current known issues
* Report new issues with detailed steps to reproduce

## Version History

* 1.0.0
  * Initial release
  * Basic item list functionality
  * Image caching
  * Unit tests
  * UI tests