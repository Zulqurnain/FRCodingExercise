package com.jutt.frinterview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.jutt.frinterview.network.ApiClient
import com.jutt.frinterview.repository.ItemRepositoryImpl
import com.jutt.frinterview.ui.itemListScreen
import com.jutt.frinterview.ui.theme.frInterviewTheme
import com.jutt.frinterview.viewmodel.ItemListViewModel

/**
 * Main activity for the FR Interview application.
 *
 * This activity serves as the entry point of the application and sets up the
 * root composable. It initializes the necessary dependencies (API client,
 * repository, and ViewModel) and configures the app's theme.
 *
 * The activity uses Jetpack Compose for its UI, setting up a full-screen
 * surface that contains the main item list screen.
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = ApiClient.createApiService()
        val repository = ItemRepositoryImpl(api)
        val viewModel = ItemListViewModel(repository)

        setContent {
            frInterviewTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    itemListScreen(viewModel = viewModel)
                }
            }
        }
    }
}
