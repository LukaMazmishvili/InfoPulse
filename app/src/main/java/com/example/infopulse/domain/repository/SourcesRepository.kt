package com.example.infopulse.domain.repository

import com.example.infopulse.common.Resource
import com.example.infopulse.data.remote.model.SourcesModelDto

interface SourcesRepository {

    suspend fun fetchSources(): Resource<SourcesModelDto>

}