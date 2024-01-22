package com.example.infopulse.domain.repository

import com.example.infopulse.common.Resource
import com.example.infopulse.data.remote.model.ArticlesModelDto

interface ArticlesRepository {
    suspend fun fetchArticles(): Resource<ArticlesModelDto>
}