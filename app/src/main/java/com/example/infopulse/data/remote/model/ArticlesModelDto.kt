package com.example.infopulse.data.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
data class ArticlesModelDto(
    val status: String,
    val id: Long,
    val articles: List<Article>
): Parcelable

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
data class Article(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String
) : Parcelable {

    @Parcelize
    data class Source(
        val id: String?,
        val name: String
    ): Parcelable
}