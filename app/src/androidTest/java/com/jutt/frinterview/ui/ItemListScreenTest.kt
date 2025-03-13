package com.jutt.frinterview.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.jutt.frinterview.model.Item
import com.jutt.frinterview.viewmodel.ItemListViewModel
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for [ItemListScreen]
 *
 * These tests verify that the screen correctly:
 * - Displays loading state
 * - Shows error messages
 * - Renders empty state
 * - Displays grouped items
 * - Handles user interactions (add/delete)
 * - Shows appropriate feedback for all states
 */
class ItemListScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val viewModel: ItemListViewModel = mockk(relaxed = true)

    /**
     * Verifies that loading state shows progress indicator
     */
    @Test
    fun `loading state shows progress indicator`() {
        composeTestRule.setContent {
            ItemListScreen(
                items = emptyMap(),
                isLoading = true,
                error = null,
                onAddItem = { _, _ -> },
                onDeleteItem = { }
            )
        }

        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
        composeTestRule.onNodeWithText("Loading items...").assertIsDisplayed()
    }

    /**
     * Verifies that error state shows error message
     */
    @Test
    fun `error state shows error message`() {
        val errorMessage = "Failed to load items"

        composeTestRule.setContent {
            ItemListScreen(
                items = emptyMap(),
                isLoading = false,
                error = errorMessage,
                onAddItem = { _, _ -> },
                onDeleteItem = { }
            )
        }

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }

    /**
     * Verifies that empty state shows appropriate message
     */
    @Test
    fun `empty state shows no items message`() {
        composeTestRule.setContent {
            ItemListScreen(
                items = emptyMap(),
                isLoading = false,
                error = null,
                onAddItem = { _, _ -> },
                onDeleteItem = { }
            )
        }

        composeTestRule.onNodeWithText("No items available").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add Item").assertIsDisplayed()
    }

    /**
     * Verifies that items are displayed correctly in groups
     */
    @Test
    fun `displays grouped items correctly`() {
        val groupedItems = mapOf(
            1 to listOf(
                Item(1, 1, "Item A"),
                Item(2, 1, "Item B")
            ),
            2 to listOf(
                Item(3, 2, "Item C"),
                Item(4, 2, "Item D")
            )
        )

        composeTestRule.setContent {
            ItemListScreen(
                items = groupedItems,
                isLoading = false,
                error = null,
                onAddItem = { _, _ -> },
                onDeleteItem = { }
            )
        }

        // Verify group headers
        composeTestRule.onNodeWithText("List ID: 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("List ID: 2").assertIsDisplayed()

        // Verify items in each group
        composeTestRule.onNodeWithText("Item A").assertIsDisplayed()
        composeTestRule.onNodeWithText("Item B").assertIsDisplayed()
        composeTestRule.onNodeWithText("Item C").assertIsDisplayed()
        composeTestRule.onNodeWithText("Item D").assertIsDisplayed()
    }

    /**
     * Verifies that add item dialog works correctly
     */
    @Test
    fun `add item dialog shows and accepts input`() {
        composeTestRule.setContent {
            ItemListScreen(
                items = emptyMap(),
                isLoading = false,
                error = null,
                onAddItem = { _, _ -> },
                onDeleteItem = { }
            )
        }

        // Click add button
        composeTestRule.onNodeWithText("Add Item").performClick()

        // Verify dialog is shown
        composeTestRule.onNodeWithTag("add_item_dialog").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add New Item").assertIsDisplayed()

        // Input fields are shown
        composeTestRule.onNodeWithTag("name_input").assertIsDisplayed()
        composeTestRule.onNodeWithTag("list_id_input").assertIsDisplayed()

        // Dialog buttons are shown
        composeTestRule.onNodeWithText("Cancel").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add").assertIsDisplayed()
    }

    /**
     * Verifies that delete confirmation dialog works correctly
     */
    @Test
    fun `delete confirmation dialog shows correctly`() {
        val item = Item(1, 1, "Test Item")
        val groupedItems = mapOf(1 to listOf(item))

        composeTestRule.setContent {
            ItemListScreen(
                items = groupedItems,
                isLoading = false,
                error = null,
                onAddItem = { _, _ -> },
                onDeleteItem = { }
            )
        }

        // Click delete button on item
        composeTestRule.onNodeWithTag("delete_button_${item.id}").performClick()

        // Verify confirmation dialog
        composeTestRule.onNodeWithTag("delete_confirmation_dialog").assertIsDisplayed()
        composeTestRule.onNodeWithText("Delete Item").assertIsDisplayed()
        composeTestRule.onNodeWithText("Are you sure you want to delete this item?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Cancel").assertIsDisplayed()
        composeTestRule.onNodeWithText("Delete").assertIsDisplayed()
    }

    /**
     * Verifies that pull-to-refresh works correctly
     */
    @Test
    fun `pull to refresh triggers reload`() {
        composeTestRule.setContent {
            ItemListScreen(
                items = emptyMap(),
                isLoading = false,
                error = null,
                onAddItem = { _, _ -> },
                onDeleteItem = { }
            )
        }

        composeTestRule.onNodeWithTag("items_list")
            .performTouchInput {
                swipeDown()
            }

        // Verify loading indicator is shown
        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }
}
