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
     * Retrieves all items from the data source.
     *
     * @return List of [Item]s sorted by their IDs
     * @throws Exception if the operation fails
     */
    suspend fun getItems(): List<Item>

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
