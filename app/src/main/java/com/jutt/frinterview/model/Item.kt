package com.jutt.frinterview.model

import com.google.gson.annotations.SerializedName

/**
 * Data class representing an item from the Fetch Rewards API.
 *
 * This class models the data structure returned by the hiring.json endpoint.
 * Items can be grouped by listId and sorted by name.
 *
 * @property id Unique identifier for the item
 * @property listId The group identifier for the item
 * @property name Optional name of the item
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
data class Item(
    @SerializedName("id")
    val id: Int,
    @SerializedName("listId")
    val listId: Int,
    @SerializedName("name")
    val name: String?,
) {
    /**
     * Validates if the item has a valid name.
     *
     * @return true if the item's name is not null or blank, false otherwise
     */
    fun isValid(): Boolean {
        return !name.isNullOrBlank()
    }
}
