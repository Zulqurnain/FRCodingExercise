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

/**
 * Dagger Hilt module for providing network-related dependencies.
 *
 * This module is responsible for providing singleton instances of network-related classes
 * that can be injected throughout the application. It is installed in the [SingletonComponent]
 * to ensure that only one instance of each dependency exists throughout the app's lifecycle.
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /**
     * Provides a singleton instance of [ApiService].
     *
     * @return The API service instance created by [ApiClient]
     */
    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiClient.createApiService()
    }

    /**
     * Provides a singleton instance of [ItemRepository].
     *
     * @param apiService The API service instance to be used by the repository
     * @return The repository implementation instance
     */
    @Provides
    @Singleton
    fun provideItemRepository(apiService: ApiService): ItemRepository {
        return ItemRepositoryImpl(apiService)
    }
}
