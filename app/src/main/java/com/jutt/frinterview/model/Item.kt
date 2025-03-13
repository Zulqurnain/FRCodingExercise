package com.jutt.frinterview.model

/**
 * Data class representing an item in the application.
 *
 * This class is used to model items retrieved from the API and displayed in the UI.
 * Each item has a unique identifier, name, and description.
 *
 * @property id Unique identifier for the item
 * @property name Display name of the item
 * @property description Detailed description of the item
 *
 * @aut hor Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
data class Item(
    val id: Int,
    val name: String,
    val description: String,
) {
    /**
     * Validates if the item has valid data.
     *
     * @return true if the item's name is not null or blank, false otherwise
     */
    fun isValid(): Boolean {
        return !name.isNullOrBlank()
    }
}
