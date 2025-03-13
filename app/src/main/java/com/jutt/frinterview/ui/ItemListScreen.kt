package com.jutt.frinterview.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jutt.frinterview.model.Item
import com.jutt.frinterview.viewmodel.ItemListViewModel

/**
 * Main screen composable for displaying the list of items.
 *
 * This composable is responsible for rendering the item list UI, including loading states,
 * error messages, and the actual list of items. It observes the ViewModel's state and
 * updates the UI accordingly.
 *
 * @param viewModel The ViewModel that manages the screen's state and business logic
 * @param modifier Modifier for customizing the layout
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
@Composable
fun itemListScreen(
    viewModel: ItemListViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }
                uiState.error != null -> {
                    Text(
                        text = uiState.error ?: "Unknown error",
                        color = MaterialTheme.colorScheme.error,
                    )
                }
                uiState.items.isEmpty() -> {
                    Text(text = "No items available")
                }
                else -> {
                    itemList(
                        items = uiState.items,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}

/**
 * Composable for rendering the list of items.
 *
 * This composable uses a LazyColumn to efficiently display a scrollable list of items.
 * Each item is displayed with its name and description.
 *
 * @param items The list of items to display
 * @param modifier Modifier for customizing the layout
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
@Composable
private fun itemList(
    items: List<Item>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
    ) {
        items(items) { item ->
            Text(
                text = "${item.name} - ${item.description}",
                modifier = Modifier.padding(vertical = 4.dp),
            )
        }
    }
}
