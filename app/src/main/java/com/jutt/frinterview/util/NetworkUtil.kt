package com.jutt.frinterview.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Utility class for checking network connectivity status.
 *
 * This singleton class provides methods to check if the device has an active
 * and validated internet connection. It uses the Android [ConnectivityManager]
 * to determine the network state.
 *
 * @property context The application context used to access system services
 *
 * @author Zulqurnain Haider (zulqurnainjj@gmail.com)
 * @since 1.0.0
 */
@Singleton
class NetworkUtil @Inject constructor(@ApplicationContext private val context: Context) {
    /**
     * Checks if the device has an active and validated internet connection.
     *
     * This method uses [ConnectivityManager] to check if:
     * 1. There is an active network connection
     * 2. The connection has internet capability
     * 3. The connection has been validated (can actually reach the internet)
     *
     * @return true if there is a valid internet connection, false otherwise
     */
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
               capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
} 