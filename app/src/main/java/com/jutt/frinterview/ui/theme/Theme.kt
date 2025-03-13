package com.jutt.frinterview.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * Dark color scheme definition for the application theme.
 * These colors are used when the device is in dark mode or when dark theme is explicitly enabled.
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
private val DarkColorScheme =
    darkColorScheme(
        primary = Purple80,
        secondary = PurpleGrey80,
        tertiary = Pink80,
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
        primary = Purple40,
        secondary = PurpleGrey40,
        tertiary = Pink40,
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
 * @param content The composable content to be themed.
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
@Composable
fun frInterviewTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
