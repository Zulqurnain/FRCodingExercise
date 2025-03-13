package com.jutt.frinterview.repository

import com.jutt.frinterview.model.Item
import com.jutt.frinterview.network.ApiService
import com.jutt.frinterview.util.NetworkUtil
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

/**
 * Unit tests for [ItemRepositoryImpl]
 *
 * These tests verify that the repository correctly:
 * - Filters out items with null or blank names
 * - Groups items by listId
 * - Sorts items within each group by name
 * - Handles network connectivity
 * - Manages caching behavior
 * - Handles API errors appropriately
 */
class ItemRepositoryTest {
    private lateinit var apiService: ApiService
    private lateinit var networkUtil: NetworkUtil
    private lateinit var repository: ItemRepository

    @Before
    fun setup() {
        apiService = mockk()
        networkUtil = mockk()
        repository = ItemRepositoryImpl(apiService, networkUtil)
    }

    /**
     * Verifies that getItems correctly filters, groups, and sorts items:
     * - Removes items with null or blank names
     * - Groups by listId
     * - Sorts by name within each group
     */
    @Test
    fun `getItems returns filtered, grouped, and sorted items`() = runTest {
        // Given
        val items = listOf(
            Item(1, 1, "Item A"),
            Item(2, 1, "Item B"),
            Item(3, 2, "Item C"),
            Item(4, 2, null),
            Item(5, 1, ""),
            Item(6, 2, "Item D"),
            Item(7, 1, "   "),
            Item(8, 3, "Z"),
            Item(9, 3, "A")
        )
        every { networkUtil.isNetworkAvailable() } returns true
        coEvery { apiService.getItems() } returns Response.success(items)

        // When
        val result = repository.getItems()

        // Then
        val expected = mapOf(
            1 to listOf(
                Item(1, 1, "Item A"),
                Item(2, 1, "Item B")
            ),
            2 to listOf(
                Item(3, 2, "Item C"),
                Item(6, 2, "Item D")
            ),
            3 to listOf(
                Item(9, 3, "A"),
                Item(8, 3, "Z")
            )
        )
        assertEquals(expected, result)
    }

    /**
     * Verifies that getItems returns cached data when network is unavailable
     */
    @Test
    fun `getItems returns cached data when offline`() = runTest {
        // First call with network to populate cache
        val items = listOf(Item(1, 1, "Item A"))
        every { networkUtil.isNetworkAvailable() } returns true
        coEvery { apiService.getItems() } returns Response.success(items)
        repository.getItems()

        // Second call without network
        every { networkUtil.isNetworkAvailable() } returns false
        val result = repository.getItems()

        val expected = mapOf(1 to listOf(Item(1, 1, "Item A")))
        assertEquals(expected, result)
    }

    /**
     * Verifies that getItems throws appropriate exception when offline with no cache
     */
    @Test
    fun `getItems throws exception when offline with no cache`() = runTest {
        every { networkUtil.isNetworkAvailable() } returns false

        try {
            repository.getItems()
            throw AssertionError("Expected exception was not thrown")
        } catch (e: Exception) {
            assertEquals("No network connection and no cached data available", e.message)
        }
    }

    /**
     * Verifies that getItems throws exception on API error
     */
    @Test
    fun `getItems throws exception on API error`() = runTest {
        every { networkUtil.isNetworkAvailable() } returns true
        coEvery { apiService.getItems() } returns Response.error(500, mockk(relaxed = true))

        try {
            repository.getItems()
            throw AssertionError("Expected exception was not thrown")
        } catch (e: Exception) {
            assert(e.message?.contains("Failed to fetch items") == true)
        }
    }

    /**
     * Verifies that addItem successfully adds a valid item
     */
    @Test
    fun `addItem successfully adds valid item`() = runTest {
        val item = Item(1, 1, "Test Item")
        every { networkUtil.isNetworkAvailable() } returns true
        coEvery { apiService.addItem(item) } returns Response.success(item)

        val result = repository.addItem(item)
        assertEquals(item, result)
    }

    /**
     * Verifies that addItem throws exception when offline
     */
    @Test
    fun `addItem throws exception when offline`() = runTest {
        val item = Item(1, 1, "Test Item")
        every { networkUtil.isNetworkAvailable() } returns false

        try {
            repository.addItem(item)
            throw AssertionError("Expected exception was not thrown")
        } catch (e: Exception) {
            assertEquals("No network connection available", e.message)
        }
    }

    /**
     * Verifies that deleteItem successfully removes an existing item
     */
    @Test
    fun `deleteItem successfully removes existing item`() = runTest {
        val itemId = 1
        every { networkUtil.isNetworkAvailable() } returns true
        coEvery { apiService.deleteItem(itemId) } returns Response.success(Unit)

        repository.deleteItem(itemId)
        // Success if no exception is thrown
    }

    /**
     * Verifies that deleteItem throws exception when offline
     */
    @Test
    fun `deleteItem throws exception when offline`() = runTest {
        val itemId = 1
        every { networkUtil.isNetworkAvailable() } returns false

        try {
            repository.deleteItem(itemId)
            throw AssertionError("Expected exception was not thrown")
        } catch (e: Exception) {
            assertEquals("No network connection available", e.message)
        }
    }
}
