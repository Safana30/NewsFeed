package com.example.newsfeed.domain.models

import com.example.newsfeed.data.apiservice.apimodels.Article
import com.example.newsfeed.data.roomdb.NewsEntity

data class ArticleBaseModel(
    val author: String ?=null,
    val content: String ?=null,
    val description: String ?=null,
    val publishedAt: String ?=null,
    val title: String ,
    val url: String ?=null,
    val urlToImage: String ?=null
){


    companion object {
        fun fromAPIModel(article: Article): ArticleBaseModel {
            return ArticleBaseModel(
                author = article.author,
                content = article.content,
                description = article.description,
                publishedAt = article.publishedAt,
                title = article.title,
                url = article.url,
                urlToImage = article.urlToImage
            )

        }

        fun fromEntity(newsEntity: NewsEntity): ArticleBaseModel {
            return ArticleBaseModel(
                author = newsEntity.author,
                content = newsEntity.content,
                description = newsEntity.description,
                publishedAt = newsEntity.publishedAt,
                title = newsEntity.title,
                url = newsEntity.url,
                urlToImage = newsEntity.urlToImage
            )
        }
    }

    fun toEntity(): NewsEntity {
        return NewsEntity(
            id = title,
            author = author,
            content =content,
            description = description,
            publishedAt =publishedAt,
            title = title,
            url = url,
            urlToImage = urlToImage
        )
    }
}
