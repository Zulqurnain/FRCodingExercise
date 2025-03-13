package com.jutt.frinterview.repository

import com.jutt.frinterview.model.Item
import com.jutt.frinterview.network.ApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Implementation of [ItemRepository] that uses a REST API as the data source.
 *
 * This class implements the repository pattern, providing a clean API for data operations
 * while abstracting the complexity of network calls and error handling.
 *
 * Each operation includes a simulated network delay to demonstrate loading states in the UI.
 *
 * @property apiService The service used to make API calls
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
class ItemRepositoryImpl
@Inject constructor(
    private val apiService: ApiService,
) : ItemRepository {
    /**
     * Retrieves all items from the API.
     *
     * @return List of [Item]s from the API
     * @throws Exception if the API call fails or returns an error response
     */
    override suspend fun getItems(): List<Item> {
        delay(1000) // Simulate network delay
        val response = apiService.getItems()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        }
        throw Exception("Failed to fetch items")
    }

    /**
     * Adds a new item through the API.
     *
     * @param item The [Item] to be added
     * @return The added [Item] with its server-assigned ID
     * @throws Exception if the API call fails or returns an error response
     */
    override suspend fun addItem(item: Item): Item {
        delay(1000) // Simulate network delay
        val response = apiService.addItem(item)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Failed to add item")
        }
        throw Exception("Failed to add item")
    }

    /**
     * Deletes an item through the API.
     *
     * @param id The ID of the item to delete
     * @throws Exception if the API call fails or returns an error response
     */
    override suspend fun deleteItem(id: Int) {
        delay(1000) // Simulate network delay
        val response = apiService.deleteItem(id)
        if (!response.isSuccessful) {
            throw Exception("Failed to delete item")
        }
    }
}
