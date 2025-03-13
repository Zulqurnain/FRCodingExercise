package com.jutt.frinterview.viewmodel

import com.jutt.frinterview.model.Item
import com.jutt.frinterview.repository.ItemRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [ItemListViewModel]
 *
 * These tests verify that the ViewModel correctly:
 * - Loads and displays items from the repository
 * - Handles loading states appropriately
 * - Manages error states
 * - Performs item operations (add/delete)
 * - Updates UI state according to operation results
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ItemListViewModelTest {
    private lateinit var repository: ItemRepository
    private lateinit var viewModel: ItemListViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = ItemListViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Verifies that loadItems successfully updates UI state with grouped and sorted items
     */
    @Test
    fun `loadItems success updates UI state with items`() = runTest {
        // Given
        val items = mapOf(
            1 to listOf(
                Item(1, 1, "Item A"),
                Item(2, 1, "Item B")
            ),
            2 to listOf(
                Item(3, 2, "Item C"),
                Item(4, 2, "Item D")
            )
        )
        coEvery { repository.getItems() } returns items

        // When
        viewModel.loadItems()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        with(viewModel.uiState.first()) {
            assertEquals(items, groupedItems)
            assertFalse(isLoading)
            assertEquals(null, error)
        }
    }

    /**
     * Verifies that loadItems shows loading state while fetching data
     */
    @Test
    fun `loadItems shows loading state`() = runTest {
        // Given
        coEvery { repository.getItems() } coAnswers {
            delay(1000)
            emptyMap()
        }

        // When
        viewModel.loadItems()

        // Then
        assertTrue(viewModel.uiState.first().isLoading)
    }

    /**
     * Verifies that loadItems handles errors appropriately
     */
    @Test
    fun `loadItems error updates UI state with error message`() = runTest {
        // Given
        val errorMessage = "Failed to fetch items"
        coEvery { repository.getItems() } throws Exception(errorMessage)

        // When
        viewModel.loadItems()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        with(viewModel.uiState.first()) {
            assertTrue(groupedItems.isEmpty())
            assertFalse(isLoading)
            assertEquals(errorMessage, error)
        }
    }

    /**
     * Verifies that addItem successfully adds an item and refreshes the list
     */
    @Test
    fun `addItem success refreshes items list`() = runTest {
        // Given
        val item = Item(1, 1, "Test Item")
        coEvery { repository.addItem(item) } returns item
        coEvery { repository.getItems() } returns mapOf(1 to listOf(item))

        // When
        viewModel.addItem(item)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        with(viewModel.uiState.first()) {
            assertEquals(mapOf(1 to listOf(item)), groupedItems)
            assertFalse(isLoading)
            assertEquals(null, error)
        }
        coVerify(exactly = 1) { repository.getItems() }
    }

    /**
     * Verifies that addItem handles errors appropriately
     */
    @Test
    fun `addItem error updates UI state with error message`() = runTest {
        // Given
        val item = Item(1, 1, "Test Item")
        val errorMessage = "Failed to add item"
        coEvery { repository.addItem(item) } throws Exception(errorMessage)

        // When
        viewModel.addItem(item)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        with(viewModel.uiState.first()) {
            assertTrue(groupedItems.isEmpty())
            assertFalse(isLoading)
            assertEquals(errorMessage, error)
        }
    }

    /**
     * Verifies that deleteItem successfully removes an item and refreshes the list
     */
    @Test
    fun `deleteItem success refreshes items list`() = runTest {
        // Given
        val itemId = 1
        coEvery { repository.deleteItem(itemId) } returns Unit
        coEvery { repository.getItems() } returns emptyMap()

        // When
        viewModel.deleteItem(itemId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        with(viewModel.uiState.first()) {
            assertTrue(groupedItems.isEmpty())
            assertFalse(isLoading)
            assertEquals(null, error)
        }
        coVerify(exactly = 1) { repository.getItems() }
    }

    /**
     * Verifies that deleteItem handles errors appropriately
     */
    @Test
    fun `deleteItem error updates UI state with error message`() = runTest {
        // Given
        val itemId = 1
        val errorMessage = "Failed to delete item"
        coEvery { repository.deleteItem(itemId) } throws Exception(errorMessage)

        // When
        viewModel.deleteItem(itemId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        with(viewModel.uiState.first()) {
            assertTrue(groupedItems.isEmpty())
            assertFalse(isLoading)
            assertEquals(errorMessage, error)
        }
    }
}
