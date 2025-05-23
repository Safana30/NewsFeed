package com.example.newsfeed.presentation.screens.mainscreen

import com.example.newsfeed.data.apiservice.apimodels.Article
import com.example.newsfeed.domain.models.ArticleBaseModel

sealed class NewsScreenStates {
    data object LOADING: NewsScreenStates()
    data class  SUCCESS(val article: List<ArticleBaseModel>?) : NewsScreenStates()
    data class  ERROR(val error : Any) : NewsScreenStates()
}