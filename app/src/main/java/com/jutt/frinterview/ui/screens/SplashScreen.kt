package com.jutt.frinterview.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jutt.frinterview.R
import com.jutt.frinterview.ui.theme.SplashBackground
import com.jutt.frinterview.ui.theme.SplashText
import com.jutt.frinterview.util.ImageCache
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Splash screen that displays while the app is initializing.
 *
 * Features:
 * - Animated logo scaling
 * - App name display
 * - Image preloading
 * - Smooth transition to main content
 *
 * @param onSplashComplete Callback to be invoked when splash screen is complete
 * @param imageCache Image cache utility for preloading images
 */
@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit,
    imageCache: ImageCache
) {
    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val infiniteTransition = rememberInfiniteTransition(label = "splash")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    LaunchedEffect(Unit) {
        // Preload images
        scope.launch {
            imageCache.cacheImage(
                "https://example.com/app_icon.png",
                "app_icon"
            )
            // Add more image preloading here
        }

        // Delay for minimum splash screen duration
        delay(2000)
        isLoading = false
        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SplashBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Icon
            val iconDescription = stringResource(R.string.app_icon_description)
            Surface(
                modifier = Modifier
                    .size(120.dp)
                    .scale(scale)
                    .semantics { contentDescription = iconDescription },
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "FR",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // App Name
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                color = SplashText,
                textAlign = TextAlign.Center
            )
        }
    }
} 