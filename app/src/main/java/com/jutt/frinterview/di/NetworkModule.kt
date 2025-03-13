package com.jutt.frinterview.di

import com.jutt.frinterview.network.ApiClient
import com.jutt.frinterview.network.ApiService
import com.jutt.frinterview.repository.ItemRepository
import com.jutt.frinterview.repository.ItemRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiClient.createApiService()
    }

    @Provides
    @Singleton
    fun provideItemRepository(apiService: ApiService): ItemRepository {
        return ItemRepositoryImpl(apiService)
    }
}
