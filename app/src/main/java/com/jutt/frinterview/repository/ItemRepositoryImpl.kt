package com.jutt.frinterview.repository

import com.jutt.frinterview.model.Item
import com.jutt.frinterview.network.ApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

class ItemRepositoryImpl
@Inject constructor(
    private val apiService: ApiService,
) : ItemRepository {
    override suspend fun getItems(): List<Item> {
        delay(1000) // Simulate network delay
        val response = apiService.getItems()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        }
        throw Exception("Failed to fetch items")
    }

    override suspend fun addItem(item: Item): Item {
        delay(1000) // Simulate network delay
        val response = apiService.addItem(item)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Failed to add item")
        }
        throw Exception("Failed to add item")
    }

    override suspend fun deleteItem(id: Int) {
        delay(1000) // Simulate network delay
        val response = apiService.deleteItem(id)
        if (!response.isSuccessful) {
            throw Exception("Failed to delete item")
        }
    }
}
