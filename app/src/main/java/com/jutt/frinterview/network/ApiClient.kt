package com.jutt.frinterview.network

import com.jutt.frinterview.model.Item
import okhttp3.Cache
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
import java.util.concurrent.TimeUnit
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * REST API service interface for item operations.
 *
 * This interface defines the contract for the Fetch Rewards hiring API endpoint.
 * It uses Retrofit annotations to define HTTP methods and endpoints.
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
    @GET("hiring.json")
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
 * Singleton class responsible for creating and configuring API-related dependencies.
 *
 * This class provides factory methods for creating Retrofit instances and API services
 * with proper configuration for network logging and JSON conversion. It also handles
 * caching and offline support.
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
@Singleton
class ApiClient @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"
        private const val CACHE_SIZE = 10 * 1024 * 1024L // 10 MB
        private const val TIMEOUT = 30L // seconds

        /**
         * Creates a new instance of [ApiService] with default configuration.
         *
         * This factory method creates a new Retrofit instance with proper configuration
         * for network logging, caching, and JSON conversion.
         *
         * @return A new instance of [ApiService]
         */
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(createOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }

        private fun createOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
        }
    }

    private val cache = Cache(File(context.cacheDir, "http_cache"), CACHE_SIZE)

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val request = chain.request()
            try {
                chain.proceed(request)
            } catch (e: Exception) {
                // If there's a network error, try to get from cache
                val offlineRequest = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=${60 * 60 * 24}")
                    .build()
                chain.proceed(offlineRequest)
            }
        }
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * The singleton instance of the API service interface.
     *
     * This property provides access to the configured API service instance
     * that can be used throughout the application.
     *
     * @return [ApiService] implementation created by Retrofit
     */
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
