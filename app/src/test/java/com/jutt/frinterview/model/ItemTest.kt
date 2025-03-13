package com.jutt.frinterview.model

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ItemTest {
    @Test
    fun `isValid should return true for non-empty name`() {
        val item = Item(1, 1, "Test Item")
        assertTrue(item.isValid())
    }

    @Test
    fun `isValid should return false for empty name`() {
        val item = Item(1, 1, "")
        assertFalse(item.isValid())
    }

    @Test
    fun `isValid should return false for blank name`() {
        val item = Item(1, 1, "   ")
        assertFalse(item.isValid())
    }

    @Test
    fun `isValid should return false for null name`() {
        val item = Item(1, 1, null)
        assertFalse(item.isValid())
    }
}
