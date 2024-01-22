package com.example.infopulse.data.remote.repository

import android.util.Log
import com.example.infopulse.common.Resource
import com.example.infopulse.data.remote.model.ArticlesModelDto
import com.example.infopulse.data.remote.services.ArticlesService
import com.example.infopulse.domain.repository.ArticlesRepository
import java.lang.Exception
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(private val articlesService: ArticlesService) :
    ArticlesRepository {
    override suspend fun fetchArticles(): Resource<ArticlesModelDto> {
        return try {

            Resource.Loading(true)

            val response = articlesService.fetchArticles()

            if (response.isSuccessful) {
                val body = response.body()
                Resource.Success(body!!)
            } else {
                Resource.Error(response.errorBody()?.string() ?: "Unknown error occurred")
            }

        } catch (e: Exception) {
            Resource.Error("Something Went Wrong : ${e.message}")
        } finally {
            //Hide Loading State
            Resource.Loading(false)
        }
    }
}


