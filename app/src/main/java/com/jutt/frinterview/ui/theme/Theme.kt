package com.jutt.frinterview.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Dark color scheme definition for the application theme.
 * These colors are used when the device is in dark mode or when dark theme is explicitly enabled.
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
private val DarkColorScheme =
    darkColorScheme(
        primary = Primary,
        secondary = Secondary,
        tertiary = SecondaryVariant,
        background = Background,
        surface = Surface,
        surfaceVariant = SurfaceVariant,
        onPrimary = OnPrimary,
        onSecondary = OnSecondary,
        onBackground = OnBackground,
        onSurface = OnSurface,
        onSurfaceVariant = OnSurfaceVariant
    )

/**
 * Light color scheme definition for the application theme.
 * These colors are used when the device is in light mode or when light theme is explicitly enabled.
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
private val LightColorScheme =
    lightColorScheme(
        primary = Primary,
        secondary = Secondary,
        tertiary = SecondaryVariant,
        background = Background,
        surface = Surface,
        surfaceVariant = SurfaceVariant,
        onPrimary = OnPrimary,
        onSecondary = OnSecondary,
        onBackground = OnBackground,
        onSurface = OnSurface,
        onSurfaceVariant = OnSurfaceVariant
    )

/* Other default colors to override
 * background = Color(0xFFFFFBFE),
 * surface = Color(0xFFFFFBFE),
 * onPrimary = Color.White,
 * onSecondary = Color.White,
 * onTertiary = Color.White,
 * onBackground = Color(0xFF1C1B1F),
 * onSurface = Color(0xFF1C1B1F),
 */

// Theme for the FR Interview app

/**
 * Main theme composable for the FR Interview application.
 *
 * This composable function sets up the Material Design theme for the entire application.
 * It automatically handles switching between light and dark themes based on the system settings,
 * unless explicitly overridden.
 *
 * Usage:
 * ```
 * frInterviewTheme {
 *     // Your app content here
 * }
 * ```
 *
 * @param darkTheme Whether to use dark theme. Defaults to the system's dark mode setting.
 * @param dynamicColor Whether to use dynamic color. Defaults to true.
 * @param content The composable content to be themed.
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
@Composable
fun FRCodingExerciseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
