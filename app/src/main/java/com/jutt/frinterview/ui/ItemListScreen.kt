package com.jutt.frinterview.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jutt.frinterview.model.Item
import com.jutt.frinterview.viewmodel.ItemListViewModel

/**
 * Main screen composable for displaying the list of items.
 *
 * This composable is responsible for rendering the item list UI, including loading states,
 * error messages, and the actual list of items grouped by listId. It observes the ViewModel's
 * state and updates the UI accordingly.
 *
 * @param viewModel The ViewModel that manages the screen's state and business logic
 * @param modifier Modifier for customizing the layout
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
@Composable
fun ItemListScreen(
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
                uiState.groupedItems.isEmpty() -> {
                    Text(text = "No items available")
                }
                else -> {
                    GroupedItemList(
                        groupedItems = uiState.groupedItems,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}

/**
 * Composable for rendering the grouped list of items.
 *
 * This composable uses a LazyColumn to efficiently display a scrollable list of items,
 * grouped by listId. Each group has a header showing its listId, followed by the items
 * in that group.
 *
 * @param groupedItems Map of listId to list of items in that group
 * @param modifier Modifier for customizing the layout
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
@Composable
private fun GroupedItemList(
    groupedItems: Map<Int, List<Item>>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.padding(16.dp),
    ) {
        groupedItems.entries.forEach { (listId, items) ->
            item {
                Text(
                    text = "List ID: $listId",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp),
                )
            }
            items(items) { item ->
                Text(
                    text = item.name ?: "",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                )
            }
        }
    }
}
