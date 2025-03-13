package com.jutt.frinterview.repository

import com.jutt.frinterview.model.Item

interface ItemRepository {
    suspend fun getItems(): List<Item>

    suspend fun addItem(item: Item): Item

    suspend fun deleteItem(id: Int)
}
