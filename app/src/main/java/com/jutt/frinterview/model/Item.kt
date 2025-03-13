package com.jutt.frinterview.model

data class Item(
    val id: Int,
    val name: String,
    val description: String,
) {
    fun isValid(): Boolean {
        return !name.isNullOrBlank()
    }
}
