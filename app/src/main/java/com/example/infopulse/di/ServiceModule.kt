package com.example.infopulse.di

import com.example.infopulse.data.remote.services.ArticlesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideArticlesService(retrofit: Retrofit): ArticlesService {
        return retrofit.create(ArticlesService::class.java)
    }

}