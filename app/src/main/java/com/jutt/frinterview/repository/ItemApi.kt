package com.jutt.frinterview.repository

import com.jutt.frinterview.model.Item
import retrofit2.http.GET

interface ItemApi {
    companion object {
        const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"
    }

    @GET("hiring.json")
    suspend fun getItems(): List<Item>
}
