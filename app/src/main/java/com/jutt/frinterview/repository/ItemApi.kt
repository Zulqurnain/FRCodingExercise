package com.jutt.frinterview.repository

import com.jutt.frinterview.model.Item
import retrofit2.http.GET

/**
 * Interface defining the API endpoints for item operations.
 *
 * This interface provides methods to interact with the item-related endpoints
 * of the API. It uses Retrofit annotations to define HTTP methods and endpoints.
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
interface ItemApi {
    companion object {
        /**
         * Base URL for the API endpoints.
         * This URL is used as the root for all API requests.
         */
        const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"
    }

    /**
     * Retrieves all items from the API.
     *
     * @return List of [Item]s from the API
     */
    @GET("hiring.json")
    suspend fun getItems(): List<Item>
}
