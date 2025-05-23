package com.example.newsfeed.domain.usecase

import android.util.Log
import com.example.newsfeed.data.roomdb.NewsEntity
import com.example.newsfeed.domain.models.ArticleBaseModel
import com.example.newsfeed.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend fun getNews() = flow {

        newsRepository.getEveryNews().let {
            if(it.isSuccessful){
                val apiArticles = it.body()?.articles.orEmpty()
                val articleBaseModels = apiArticles.map { ArticleBaseModel.fromAPIModel(it) }

                // Store in DB
                val entities = articleBaseModels.map { it.toEntity() }
                newsRepository.insertNewsToDb(entities)

                emit(articleBaseModels)

            }
        }
    }
}