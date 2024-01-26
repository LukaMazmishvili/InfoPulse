package com.example.infopulse.data.local.model

data class NewsEntity(
    val source: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String?,
    val imageBlob: ByteArray?,
    val publishedAt: String,
    val content: String
)

