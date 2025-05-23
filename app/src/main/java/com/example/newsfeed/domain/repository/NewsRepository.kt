package com.example.newsfeed.domain.repository

import com.example.newsfeed.data.apiservice.apimodels.Article
import com.example.newsfeed.data.apiservice.apimodels.Articles
import com.example.newsfeed.data.roomdb.NewsDao
import com.example.newsfeed.data.roomdb.NewsEntity
import com.example.newsfeed.domain.models.ArticleBaseModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsRepository {

    suspend fun getEveryNews(): Response<Articles>

    suspend fun getNewsFromDb(): Flow<List<ArticleBaseModel>>

    suspend fun insertNewsToDb(newsList: List<NewsEntity>)

    fun setPeriodicWorkRequest()
}