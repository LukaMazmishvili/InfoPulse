package com.example.infopulse.di

import com.example.infopulse.data.remote.repository.ArticlesRepositoryImpl
import com.example.infopulse.data.remote.services.ArticlesService
import com.example.infopulse.domain.repository.ArticlesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesArticlesRepository(articlesService: ArticlesService): ArticlesRepository {
        return ArticlesRepositoryImpl(articlesService)
    }

}