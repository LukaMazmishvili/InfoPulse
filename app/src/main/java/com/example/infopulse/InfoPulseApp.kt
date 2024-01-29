package com.example.infopulse

import android.app.Application
import com.example.infopulse.worker.NewsApiWorkerBuilder
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltAndroidApp
class InfoPulseApp: Application() {

    override fun onCreate() {
        super.onCreate()

        NewsApiWorkerBuilder.enqueueWork()
    }

}