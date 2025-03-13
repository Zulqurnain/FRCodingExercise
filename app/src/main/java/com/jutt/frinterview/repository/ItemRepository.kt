package com.jutt.frinterview.repository

import com.jutt.frinterview.model.Item

/**
 * Repository interface for managing Item data operations.
 *
 * This interface defines the contract for data operations related to [Item]s.
 * It abstracts the data source implementation details from the rest of the application,
 * allowing for easy switching between different data sources (e.g., API, database).
 *
 * All methods are suspend functions to support asynchronous operations.
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
interface ItemRepository {
    /**
     * Retrieves all valid items from the data source.
     *
     * The items are:
     * 1. Filtered to remove those with null or blank names
     * 2. Grouped by listId
     * 3. Sorted by name within each group
     *
     * @return Map of listId to sorted list of [Item]s
     * @throws Exception if the operation fails
     */
    suspend fun getItems(): Map<Int, List<Item>>

    /**
     * Adds a new item to the data source.
     *
     * @param item The [Item] to be added
     * @return The added [Item] with its assigned ID
     * @throws Exception if the operation fails or the item is invalid
     */
    suspend fun addItem(item: Item): Item

    /**
     * Deletes an item from the data source.
     *
     * @param id The ID of the item to delete
     * @throws Exception if the operation fails or the item doesn't exist
     */
    suspend fun deleteItem(id: Int)
}
