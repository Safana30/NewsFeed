package com.example.newsfeed.di

import androidx.work.WorkManager
import com.example.newsfeed.data.apiservice.ApiInterface
import com.example.newsfeed.data.repositoryimpl.NewsRepositoryImpl
import com.example.newsfeed.data.roomdb.NewsDao
import com.example.newsfeed.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun getNews(
        apiInterface: ApiInterface,
        newsDao: NewsDao,
        workManager: WorkManager
    ): NewsRepository = NewsRepositoryImpl(
        apiInterface = apiInterface, newsDao = newsDao, workManager = workManager
    )
}