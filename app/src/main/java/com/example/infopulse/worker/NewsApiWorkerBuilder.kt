package com.example.infopulse.worker

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object NewsApiWorkerBuilder {

    fun build(): PeriodicWorkRequest {
        // Define constraints for when the work should run
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // Require network connectivity
            .build()

        // Create a periodic work request
        return PeriodicWorkRequest.Builder(
            NewsApiWorker::class.java,
            repeatInterval = 15, // Repeat every 15 minutes
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()
    }

    fun enqueueWork() {
        // Enqueue the work request
        val workRequest = build()
        WorkManager.getInstance().enqueue(workRequest)
    }
}