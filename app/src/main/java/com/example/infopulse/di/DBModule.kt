package com.example.infopulse.di

import android.content.Context
import com.example.infopulse.data.local.DBHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun providesDb(context: Context): DBHelper {
        return DBHelper(context)
    }

}