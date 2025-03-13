package com.jutt.frinterview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jutt.frinterview.model.Item
import com.jutt.frinterview.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Data class representing the UI state for the item list screen.
 *
 * This class encapsulates all the information needed to render the item list UI,
 * including loading state, items, and error messages.
 *
 * @property isLoading Whether the UI is currently loading data
 * @property items The list of items to display
 * @property error Any error message to display, null if no error
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
data class ItemListUiState(
    val isLoading: Boolean = false,
    val items: List<Item> = emptyList(),
    val error: String? = null,
)

/**
 * ViewModel for managing the item list screen's business logic and state.
 *
 * This class is responsible for managing the UI state of the item list screen,
 * handling user actions, and coordinating with the repository for data operations.
 * It uses Kotlin Flows for reactive state management and coroutines for asynchronous operations.
 *
 * @property repository The repository used for data operations
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
@HiltViewModel
class ItemListViewModel
@Inject constructor(
    private val repository: ItemRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ItemListUiState())
    val uiState: StateFlow<ItemListUiState> = _uiState.asStateFlow()

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
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val items = repository.getItems()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        items = items,
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error",
                    )
                }
            }
        }
    }

    /**
     * Adds a new item to the repository.
     *
     * @param name The name of the item to add
     * @param description The description of the item to add
     */
    fun addItem(
        name: String,
        description: String,
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val newItem = Item(
                    id = 0,
                    name = name,
                    description = description,
                )
                val addedItem = repository.addItem(newItem)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        items = it.items + addedItem,
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error",
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
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        items = it.items.filter { item -> item.id != id },
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error",
                    )
                }
            }
        }
    }
}
