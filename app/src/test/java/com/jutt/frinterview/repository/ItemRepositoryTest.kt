package com.jutt.frinterview.repository

import com.jutt.frinterview.model.Item
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ItemRepositoryTest {
    private lateinit var api: ItemApi
    private lateinit var repository: ItemRepository

    @Before
    fun setup() {
        api = mockk()
        repository = ItemRepositoryImpl(api)
    }

    @Test
    fun `test getItems returns sorted and filtered items`() =
        runTest {
            val items =
                listOf(
                    Item(1, 1, "Item A"),
                    Item(2, 1, "Item B"),
                    Item(3, 2, "Item C"),
                )
            coEvery { api.getItems() } returns items

            val result = repository.getItems()

            assert(result == items.sortedWith(compareBy({ it.listId }, { it.name })))
        }

    @Test
    fun `test addItem adds valid items`() =
        runTest {
            val item = Item(1, 1, "Test Item")
            coEvery { api.addItem(item) } returns true

            val result = repository.addItem(item)

            assert(result)
        }

    @Test
    fun `test addItem rejects invalid items`() =
        runTest {
            val item = Item(1, 1, null)
            coEvery { api.addItem(item) } returns false

            val result = repository.addItem(item)

            assert(!result)
        }

    @Test
    fun `test deleteItem removes existing item`() =
        runTest {
            val itemId = 1
            coEvery { api.deleteItem(itemId) } returns true

            val result = repository.deleteItem(itemId)

            assert(result)
        }

    @Test
    fun `test deleteItem returns false for non-existent item`() =
        runTest {
            val itemId = 999
            coEvery { api.deleteItem(itemId) } returns false

            val result = repository.deleteItem(itemId)

            assert(!result)
        }
}
