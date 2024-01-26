package com.example.infopulse.data.converter

import com.example.infopulse.data.local.model.NewsEntity
import com.example.infopulse.data.remote.model.Article


fun convertArticleToNewsEntity(article: Article): NewsEntity {
    return NewsEntity(
        source = article.source.name,
        author = article.author,
        title = article.title,
        description = article.description,
        url = article.url,
        urlToImage = article.urlToImage,
        imageBlob = null,
        publishedAt = article.publishedAt,
        content = article.content
    )
}