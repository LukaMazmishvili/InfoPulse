package com.example.infopulse.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.infopulse.common.Resource
import com.example.infopulse.data.remote.model.ArticlesModelDto
import com.example.infopulse.domain.repository.ArticlesRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class NewsApiWorker @Inject constructor(
    appContext: Context,
    workerParams: WorkerParameters,
    private val repository: ArticlesRepository
) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        fetchDataFromApi()
        return Result.success()
    }

    private fun fetchDataFromApi() {
        runBlocking {
            try {
                val data: Resource<ArticlesModelDto> = repository.fetchArticles()

                if (data is Resource.Success) {
                    val articlesModelDto = data.data
                    Log.d("ExampleApiWorker", "Data received: $articlesModelDto")
                } else {
                    Log.e("ExampleApiWorker", "Something Went Wrong!")
                }
            } catch (e: Exception) {
                // Handle exceptions
                Log.e("ExampleApiWorker", "API request failed", e)
            }
        }
    }
}