package com.jutt.frinterview.di

import android.content.Context
import com.jutt.frinterview.network.ApiClient
import com.jutt.frinterview.network.ApiService
import com.jutt.frinterview.repository.ItemRepository
import com.jutt.frinterview.repository.ItemRepositoryImpl
import com.jutt.frinterview.util.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
     * Provides a singleton instance of [NetworkUtil].
     *
     * @param context The application context required for network operations
     * @return The NetworkUtil instance for checking network connectivity
     */
    @Provides
    @Singleton
    fun provideNetworkUtil(
        @ApplicationContext context: Context
    ): NetworkUtil {
        return NetworkUtil(context)
    }

    /**
     * Provides a singleton instance of [ApiService].
     *
     * @return The API service instance created by [ApiClient]
     */
    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return ApiClient.create()
    }

    /**
     * Provides a singleton instance of [ItemRepository].
     *
     * @param apiService The API service instance to be used by the repository
     * @param networkUtil The NetworkUtil instance for checking connectivity
     * @return The repository implementation instance
     */
    @Provides
    @Singleton
    fun provideItemRepository(
        apiService: ApiService,
        networkUtil: NetworkUtil
    ): ItemRepository {
        return ItemRepositoryImpl(apiService, networkUtil)
    }
}
