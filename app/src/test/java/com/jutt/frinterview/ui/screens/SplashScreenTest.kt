package com.jutt.frinterview.ui.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.jutt.frinterview.util.ImageCache
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class SplashScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `SplashScreen displays app name and icon`() {
        // Given
        val imageCache = mockk<ImageCache>()
        var onSplashCompleteCalled = false

        // When
        composeTestRule.setContent {
            SplashScreen(
                onSplashComplete = { onSplashCompleteCalled = true },
                imageCache = imageCache
            )
        }

        // Then
        composeTestRule.onNodeWithText("FR Coding Exercise").assertExists()
        composeTestRule.onNodeWithContentDescription("App Icon").assertExists()
    }

    @Test
    fun `SplashScreen preloads images`() {
        // Given
        val imageCache = mockk<ImageCache>()
        val imageUrl = "https://example.com/app_icon.png"
        val imageKey = "app_icon"

        coEvery { imageCache.cacheImage(imageUrl, imageKey) } returns mockk()

        // When
        composeTestRule.setContent {
            SplashScreen(
                onSplashComplete = {},
                imageCache = imageCache
            )
        }

        // Then
        // Wait for the LaunchedEffect to complete
        Thread.sleep(2100)
        coEvery { imageCache.cacheImage(imageUrl, imageKey) }
    }

    @Test
    fun `SplashScreen calls onSplashComplete after delay`() {
        // Given
        val imageCache = mockk<ImageCache>()
        var onSplashCompleteCalled = false

        // When
        composeTestRule.setContent {
            SplashScreen(
                onSplashComplete = { onSplashCompleteCalled = true },
                imageCache = imageCache
            )
        }

        // Then
        // Wait for the LaunchedEffect to complete
        Thread.sleep(2100)
        assert(onSplashCompleteCalled) { "onSplashComplete should be called after delay" }
    }
} 