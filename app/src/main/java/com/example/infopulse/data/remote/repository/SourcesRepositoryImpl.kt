package com.example.infopulse.data.remote.repository

import com.example.infopulse.common.Resource
import com.example.infopulse.data.remote.model.SourcesModelDto
import com.example.infopulse.data.remote.services.SourcesService
import com.example.infopulse.domain.repository.SourcesRepository
import javax.inject.Inject

class SourcesRepositoryImpl @Inject constructor(private val sourcesService: SourcesService) :
    SourcesRepository {
    override suspend fun fetchSources(): Resource<SourcesModelDto> {
        return try {

            Resource.Loading(true)

            val response = sourcesService.fetchSources()

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