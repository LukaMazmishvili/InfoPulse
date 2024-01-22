package com.example.infopulse.data.remote.services

import com.example.infopulse.common.Endpoints.EVERYTHING_GLOBAL_ENDPOINT
import com.example.infopulse.data.remote.model.ArticlesModelDto
import retrofit2.Response
import retrofit2.http.GET

interface ArticlesService {

    @GET(EVERYTHING_GLOBAL_ENDPOINT)
    suspend fun fetchArticles() : Response<ArticlesModelDto>

}