package com.jutt.frinterview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.jutt.frinterview.ui.ItemListScreen
import com.jutt.frinterview.ui.theme.FRCodingExerciseTheme
import com.jutt.frinterview.viewmodel.ItemListViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity for the FR Interview application.
 *
 * This activity serves as the entry point of the application and sets up the
 * root composable. It uses Hilt for dependency injection to obtain the ViewModel
 * and configures the app's theme.
 *
 * The activity uses Jetpack Compose for its UI, setting up a full-screen
 * surface that contains the main item list screen.
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ItemListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FRCodingExerciseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    ItemListScreen(viewModel = viewModel)
                }
            }
        }
    }
}
