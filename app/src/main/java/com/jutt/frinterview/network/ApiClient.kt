package com.jutt.frinterview.network

import com.jutt.frinterview.model.Item
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * REST API service interface for item operations.
 *
 * This interface defines the contract for all API endpoints used in the application.
 * It uses Retrofit annotations to define HTTP methods, endpoints, and request/response formats.
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
interface ApiService {
    /**
     * Retrieves all items from the server.
     *
     * @return [Response] containing a list of [Item]s if successful
     */
    @GET("items")
    suspend fun getItems(): Response<List<Item>>

    /**
     * Adds a new item to the server.
     *
     * @param item The [Item] to be added
     * @return [Response] containing the added [Item] with its server-assigned ID if successful
     */
    @POST("items")
    suspend fun addItem(
        @Body item: Item,
    ): Response<Item>

    /**
     * Deletes an item from the server.
     *
     * @param id The ID of the item to delete
     * @return [Response] with no content if successful
     */
    @DELETE("items/{id}")
    suspend fun deleteItem(
        @Path("id") id: Int,
    ): Response<Unit>
}

/**
 * Singleton object responsible for creating and configuring API-related dependencies.
 *
 * This object provides factory methods for creating Retrofit instances and API services
 * with proper configuration for network logging and JSON conversion.
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
object ApiClient {
    /**
     * Creates a configured Retrofit instance.
     *
     * @return [Retrofit] instance configured with OkHttp client and Gson converter
     */
    fun createApi(): Retrofit {
        val loggingInterceptor =
            HttpLoggingInterceptor()
                .apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

        val client =
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        return Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Creates an instance of the API service interface.
     *
     * @return [ApiService] implementation created by Retrofit
     */
    fun createApiService(): ApiService {
        return createApi().create(ApiService::class.java)
    }
}
