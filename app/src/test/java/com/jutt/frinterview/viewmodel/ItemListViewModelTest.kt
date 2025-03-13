package com.jutt.frinterview.viewmodel

import com.jutt.frinterview.model.Item
import com.jutt.frinterview.repository.ItemRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ItemListViewModelTest {
    private lateinit var repository: ItemRepository
    private lateinit var viewModel: ItemListViewModel
    private val testDispatcher: TestDispatcher =
        UnconfinedTestDispatcher()

    @Before
    fun setup() {
        repository = mockk()
        viewModel = ItemListViewModel(repository)
    }

    @Test
    fun loadItems_success() =
        runTest {
            val items =
                listOf(
                    Item(1, 1, "Item A"),
                    Item(2, 1, "Item B"),
                    Item(3, 2, "Item C"),
                )
            coEvery { repository.getItems() } returns items

            viewModel.loadItems()

            assertEquals(items, viewModel.uiState.value.items)
            assertFalse(viewModel.uiState.value.isLoading)
            assertEquals(null, viewModel.uiState.value.error)
        }

    @Test
    fun loadItems_error() =
        runTest {
            val errorMessage = "Failed to load items"
            coEvery { repository.getItems() } throws Exception(errorMessage)

            viewModel.loadItems()

            assertTrue(viewModel.uiState.value.items.isEmpty())
            assertFalse(viewModel.uiState.value.isLoading)
            assertEquals(errorMessage, viewModel.uiState.value.error)
        }

    @Test
    fun addItem_success() =
        runTest {
            val listId = 1
            val name = "New Item"
            coEvery { repository.addItem(listId, name) } returns true

            viewModel.addItem(listId, name)

            assertFalse(viewModel.uiState.value.isLoading)
            assertEquals(null, viewModel.uiState.value.error)
        }

    @Test
    fun addItem_error() =
        runTest {
            val listId = 1
            val name = "New Item"
            coEvery { repository.addItem(listId, name) } returns false

            viewModel.addItem(listId, name)

            assertFalse(viewModel.uiState.value.isLoading)
            assertEquals("Failed to add item", viewModel.uiState.value.error)
        }

    @Test
    fun deleteItem_success() =
        runTest {
            val itemId = 1
            coEvery { repository.deleteItem(itemId) } returns true

            viewModel.deleteItem(itemId)

            assertFalse(viewModel.uiState.value.isLoading)
            assertEquals(null, viewModel.uiState.value.error)
        }

    @Test
    fun deleteItem_error() =
        runTest {
            val itemId = 1
            coEvery { repository.deleteItem(itemId) } returns false

            viewModel.deleteItem(itemId)

            assertFalse(viewModel.uiState.value.isLoading)
            assertEquals("Failed to delete item", viewModel.uiState.value.error)
        }
}
