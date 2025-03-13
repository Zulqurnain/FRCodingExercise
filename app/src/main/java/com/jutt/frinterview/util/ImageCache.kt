package com.jutt.frinterview.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Utility class for caching images in the application.
 *
 * This singleton class provides methods to:
 * - Download images from URLs
 * - Cache images in the app's private storage
 * - Retrieve cached images
 * - Clear the image cache
 *
 * @property context The application context used to access private storage
 */
@Singleton
class ImageCache @Inject constructor(@ApplicationContext private val context: Context) {
    private val cacheDir: File = context.cacheDir.resolve("image_cache").apply { mkdirs() }

    /**
     * Downloads and caches an image from a URL.
     *
     * @param url The URL of the image to download
     * @param key The unique key to identify the cached image
     * @return The downloaded and cached bitmap, or null if the operation fails
     */
    suspend fun cacheImage(url: String, key: String): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val file = File(cacheDir, key)
            if (!file.exists()) {
                val bitmap = URL(url).openStream().use { inputStream ->
                    BitmapFactory.decodeStream(inputStream)
                }
                FileOutputStream(file).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                }
            }
            BitmapFactory.decodeFile(file.absolutePath)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Retrieves a cached image by its key.
     *
     * @param key The unique key of the cached image
     * @return The cached bitmap, or null if not found
     */
    suspend fun getCachedImage(key: String): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val file = File(cacheDir, key)
            if (file.exists()) {
                BitmapFactory.decodeFile(file.absolutePath)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Clears all cached images.
     */
    suspend fun clearCache() = withContext(Dispatchers.IO) {
        cacheDir.listFiles()?.forEach { it.delete() }
    }
} 