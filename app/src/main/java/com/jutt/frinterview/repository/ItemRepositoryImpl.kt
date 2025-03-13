package com.jutt.frinterview.repository

import com.jutt.frinterview.model.Item
import com.jutt.frinterview.network.ApiService
import com.jutt.frinterview.util.NetworkUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of the ItemRepository interface that handles data operations.
 *
 * This class is responsible for managing item data through the API client.
 * It includes methods for retrieving items, filtering out invalid ones,
 * and sorting them according to the requirements.
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
@Singleton
class ItemRepositoryImpl
    @Inject
    constructor(
        private val apiService: ApiService,
        private val networkUtil: NetworkUtil,
    ) : ItemRepository {
    private var cachedItems: Map<Int, List<Item>>? = null

    /**
     * Retrieves all valid items from the API, filtered and sorted.
     *
     * This method:
     * 1. Fetches items from the API
     * 2. Filters out items with null or blank names
     * 3. Groups items by listId
     * 4. Sorts items within each group by name
     *
     * @return Map of listId to sorted list of [Item]s
     * @throws Exception if the API call fails or returns an error response
     */
    override suspend fun getItems(): Map<Int, List<Item>> {
        // Return cached items if no network
        if (!networkUtil.isNetworkAvailable()) {
            return cachedItems ?: throw Exception("No network connection and no cached data available")
        }

        try {
            val response = apiService.getItems()
            if (response.isSuccessful) {
                return response.body()?.let { items ->
                    items
                        .filter { it.isValid() }
                        .groupBy { it.listId }
                        .mapValues { (_, items) -> items.sortedBy { it.name } }
                        .toSortedMap()
                        .also { cachedItems = it }
                } ?: emptyMap()
            }
            throw Exception(getErrorMessage(response) ?: "Failed to fetch items")
        } catch (e: Exception) {
            // If we have cached items, return them during network errors
            cachedItems?.let { return it }
            throw Exception("Failed to fetch items: ${e.localizedMessage}")
        }
    }

    /**
     * Adds a new item through the API.
     *
     * @param item The [Item] to be added
     * @return The added [Item] with its server-assigned ID
     * @throws Exception if the API call fails or returns an error response
     */
    override suspend fun addItem(item: Item): Item {
        if (!networkUtil.isNetworkAvailable()) {
            throw Exception("No network connection available")
        }

        delay(1000) // Simulate network delay
        try {
            val response = apiService.addItem(item)
            if (response.isSuccessful) {
                return response.body() ?: throw Exception("Invalid response body")
            }
            throw Exception(getErrorMessage(response) ?: "Failed to add item")
        } catch (e: Exception) {
            throw Exception("Failed to add item: ${e.localizedMessage}")
        }
    }

    /**
     * Deletes an item through the API.
     *
     * @param id The ID of the item to delete
     * @throws Exception if the API call fails or returns an error response
     */
    override suspend fun deleteItem(id: Int) {
        if (!networkUtil.isNetworkAvailable()) {
            throw Exception("No network connection available")
        }

        delay(1000) // Simulate network delay
        try {
            val response = apiService.deleteItem(id)
            if (!response.isSuccessful) {
                throw Exception(getErrorMessage(response) ?: "Failed to delete item")
            }
            // Clear cache after successful delete
            cachedItems = null
        } catch (e: Exception) {
            throw Exception("Failed to delete item: ${e.localizedMessage}")
        }
    }

    private fun <T> getErrorMessage(response: Response<T>): String? {
        return if (!response.isSuccessful) {
            "Error ${response.code()}: ${response.errorBody()?.string()}"
        } else {
            null
        }
    }
}
