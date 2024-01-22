package com.example.infopulse.data.remote.services

import com.example.infopulse.common.Endpoints.SOURCES
import com.example.infopulse.data.remote.model.SourcesModelDto
import retrofit2.Response
import retrofit2.http.GET

interface SourcesService {

    @GET(SOURCES)
    suspend fun fetchSources() : Response<SourcesModelDto>
}