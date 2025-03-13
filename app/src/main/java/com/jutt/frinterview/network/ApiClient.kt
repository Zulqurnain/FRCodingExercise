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

interface ApiService {
    @GET("items")
    suspend fun getItems(): Response<List<Item>>

    @POST("items")
    suspend fun addItem(
        @Body item: Item,
    ): Response<Item>

    @DELETE("items/{id}")
    suspend fun deleteItem(
        @Path("id") id: Int,
    ): Response<Unit>
}

object ApiClient {
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

    fun createApiService(): ApiService {
        return createApi().create(ApiService::class.java)
    }
}
