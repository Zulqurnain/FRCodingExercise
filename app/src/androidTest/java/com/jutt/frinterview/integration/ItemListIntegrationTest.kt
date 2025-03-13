package com.jutt.frinterview.integration

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jutt.frinterview.model.Item
import com.jutt.frinterview.repository.ItemRepository
import com.jutt.frinterview.ui.ItemListScreen
import com.jutt.frinterview.viewmodel.ItemListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemListIntegrationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var repository: ItemRepository
    private lateinit var viewModel: ItemListViewModel

    @Before
    fun setup() {
        repository = mockk()
        viewModel = ItemListViewModel(repository)
    }

    @Test
    fun itemListScreen_displaysItems() {
        val items =
            listOf(
                Item(1, 1, "Item A"),
                Item(2, 1, "Item B"),
                Item(3, 2, "Item C"),
            )

        composeTestRule.setContent {
            ItemListScreen(
                items = items,
                isLoading = false,
                error = null,
                onAddItem = { _, _ -> },
                onDeleteItem = { },
            )
        }

        composeTestRule.onNodeWithText("List ID: 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Item A").assertIsDisplayed()
        composeTestRule.onNodeWithText("Item B").assertIsDisplayed()
        composeTestRule.onNodeWithText("List ID: 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("Item C").assertIsDisplayed()
    }

    @Test
    fun displaysError_whenLoadingFails() {
        val errorMessage = "Failed to load items"
        coEvery { repository.getItems() } throws Exception(errorMessage)

        composeTestRule.setContent {
            ItemListScreen(
                items = emptyList(),
                isLoading = true,
                error = errorMessage,
                onAddItem = { _, _ -> },
                onDeleteItem = { },
            )
        }

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun displaysNoItemsFound_whenEmptyList() {
        coEvery { repository.getItems() } returns emptyList()

        composeTestRule.setContent {
            ItemListScreen(
                items = emptyList(),
                isLoading = false,
                error = null,
                onAddItem = { _, _ -> },
                onDeleteItem = { },
            )
        }

        composeTestRule.onNodeWithText("No items found").assertIsDisplayed()
    }
}
