package com.jutt.frinterview.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ImageCacheTest {
    private lateinit var context: Context
    private lateinit var cacheDir: File
    private lateinit var imageCache: ImageCache

    @Before
    fun setup() {
        context = mockk()
        cacheDir = mockk()
        every { context.cacheDir } returns cacheDir
        every { cacheDir.resolve("image_cache") } returns cacheDir
        every { cacheDir.mkdirs() } returns true
        imageCache = ImageCache(context)
    }

    @Test
    fun `cacheImage downloads and caches image successfully`() = runBlocking {
        // Given
        val url = "https://example.com/image.png"
        val key = "test_image"
        val mockBitmap = mockk<Bitmap>()
        val mockFile = mockk<File>()
        val mockFileOutputStream = mockk<FileOutputStream>()

        every { File(cacheDir, key) } returns mockFile
        every { mockFile.exists() } returns false
        every { URL(url).openStream() } returns mockk()
        every { BitmapFactory.decodeStream(any()) } returns mockBitmap
        every { FileOutputStream(mockFile) } returns mockFileOutputStream
        every { BitmapFactory.decodeFile(mockFile.absolutePath) } returns mockBitmap

        // When
        val result = imageCache.cacheImage(url, key)

        // Then
        assertNotNull(result)
        assertEquals(mockBitmap, result)
    }

    @Test
    fun `getCachedImage returns cached image when exists`() = runBlocking {
        // Given
        val key = "test_image"
        val mockBitmap = mockk<Bitmap>()
        val mockFile = mockk<File>()

        every { File(cacheDir, key) } returns mockFile
        every { mockFile.exists() } returns true
        every { BitmapFactory.decodeFile(mockFile.absolutePath) } returns mockBitmap

        // When
        val result = imageCache.getCachedImage(key)

        // Then
        assertNotNull(result)
        assertEquals(mockBitmap, result)
    }

    @Test
    fun `getCachedImage returns null when image not found`() = runBlocking {
        // Given
        val key = "test_image"
        val mockFile = mockk<File>()

        every { File(cacheDir, key) } returns mockFile
        every { mockFile.exists() } returns false

        // When
        val result = imageCache.getCachedImage(key)

        // Then
        assertNull(result)
    }

    @Test
    fun `clearCache deletes all files in cache directory`() = runBlocking {
        // Given
        val mockFiles = arrayOf(mockk<File>(), mockk<File>())
        every { cacheDir.listFiles() } returns mockFiles
        every { mockFiles[0].delete() } returns true
        every { mockFiles[1].delete() } returns true

        // When
        imageCache.clearCache()

        // Then
        // Verify that delete was called on each file
        mockFiles.forEach { file ->
            assertEquals(true, file.delete())
        }
    }
} 