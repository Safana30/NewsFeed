package com.example.newsfeed.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.newsfeed.data.apiservice.apimodels.Article
import com.example.newsfeed.domain.models.ArticleBaseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

const val SHARED_VIEW_MODEL="sharedViewModel"
@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    private val _selectedArticle = MutableStateFlow<ArticleBaseModel?>(null)
    val selectedArticle = _selectedArticle.asStateFlow()

    init {
        Log.d(SHARED_VIEW_MODEL, "selected: $selectedArticle ")
    }
    fun setArticle(article: ArticleBaseModel) {
        _selectedArticle.value = article
    }
}