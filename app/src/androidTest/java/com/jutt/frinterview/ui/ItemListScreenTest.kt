package com.jutt.frinterview.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.jutt.frinterview.model.Item
import org.junit.Rule
import org.junit.Test

class ItemListScreenTest {
    @get:Rule
    val composeTestRule =
        createAndroidComposeRule<ComponentActivity>()

    @Test
    fun itemListScreen_displaysLoadingState() {
        composeTestRule.setContent {
            ItemListScreen(
                items = emptyList(),
                isLoading = true,
                error = null,
                onAddItem = { _, _ -> },
                onDeleteItem = { },
            )
        }

        composeTestRule.onNodeWithText("Loading...").assertIsDisplayed()
    }

    @Test
    fun itemListScreen_displaysErrorState() {
        val errorMessage = "Error loading items"

        composeTestRule.setContent {
            ItemListScreen(
                items = emptyList(),
                isLoading = false,
                error = errorMessage,
                onAddItem = { _, _ -> },
                onDeleteItem = { },
            )
        }

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun itemListScreen_displaysEmptyState() {
        composeTestRule.setContent {
            ItemListScreen(
                items = emptyList(),
                isLoading = false,
                error = null,
                onAddItem = { _, _ -> },
                onDeleteItem = { },
            )
        }

        composeTestRule.onNodeWithText("No items available").assertIsDisplayed()
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
}
