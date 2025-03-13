package com.jutt.frinterview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jutt.frinterview.model.Item
import com.jutt.frinterview.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Data class representing the UI state for the item list screen.
 *
 * @property groupedItems The map of listId to sorted list of items
 * @property isLoading Whether the data is currently being loaded
 * @property error Any error message to display
 */
data class ItemListUiState(
    val groupedItems: Map<Int, List<Item>> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

/**
 * ViewModel for managing the item list screen's UI state and business logic.
 *
 * This class handles loading and displaying items grouped by listId.
 * It uses Kotlin Flows to manage and expose UI state changes.
 *
 * @property repository The repository used to perform data operations
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
@HiltViewModel
class ItemListViewModel
    @Inject
    constructor(
        private val repository: ItemRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(ItemListUiState())
        val uiState: StateFlow<ItemListUiState> = _uiState.asStateFlow()

        private var loadItemsJob: Job? = null

        init {
            loadItems()
        }

        /**
         * Loads items from the repository.
         *
         * Updates the UI state with loading indicator while fetching data,
         * and handles both success and error cases.
         */
        fun loadItems() {
            loadItemsJob?.cancel()
            loadItemsJob = viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null) }
                try {
                    val items = repository.getItems()
                    _uiState.update { 
                        it.copy(
                            groupedItems = items,
                            isLoading = false,
                            error = null
                        )
                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: "Failed to load items"
                        )
                    }
                }
            }
        }

        /**
         * Adds a new item to the repository.
         *
         * @param item The item to add
         */
        fun addItem(item: Item) {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null) }
                try {
                    val newItem = repository.addItem(item)
                    _uiState.update { state ->
                        val currentItems = state.groupedItems.toMutableMap()
                        val listItems = currentItems[item.listId]?.toMutableList() ?: mutableListOf()
                        listItems.add(newItem)
                        listItems.sortBy { it.name }
                        currentItems[item.listId] = listItems
                        state.copy(
                            groupedItems = currentItems.toSortedMap(),
                            isLoading = false,
                            error = null
                        )
                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: "Failed to add item"
                        )
                    }
                }
            }
        }

        /**
         * Deletes an item from the repository.
         *
         * @param id The ID of the item to delete
         */
        fun deleteItem(id: Int) {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, error = null) }
                try {
                    repository.deleteItem(id)
                    _uiState.update { state ->
                        state.copy(
                            groupedItems = state.groupedItems.mapValues { (_, items) -> 
                                items.filter { it.id != id } 
                            }.filterValues { it.isNotEmpty() },
                            isLoading = false,
                            error = null
                        )
                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: "Failed to delete item"
                        )
                    }
                }
            }
        }

        override fun onCleared() {
            super.onCleared()
            loadItemsJob?.cancel()
        }
    }
