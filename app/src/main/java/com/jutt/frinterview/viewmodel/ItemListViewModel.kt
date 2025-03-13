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

data class ItemListUiState(
    val isLoading: Boolean = false,
    val items: List<Item> = emptyList(),
    val error: String? = null,
)

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
