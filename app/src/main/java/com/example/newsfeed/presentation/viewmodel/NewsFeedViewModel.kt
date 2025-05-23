package com.example.newsfeed.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.domain.repository.NewsRepository
import com.example.newsfeed.domain.usecase.NewsUseCase
import com.example.newsfeed.presentation.screens.mainscreen.NewsScreenStates
import com.example.newsfeed.utils.ConnectivityObserver
import com.example.newsfeed.utils.NetworkConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val NEWS = "NEWS"
@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase,
    private val newsRepository: NewsRepository,
    private val connectivityObserver: NetworkConnectivityObserver,
): ViewModel() {

    private val _newsData = MutableStateFlow<NewsScreenStates>(NewsScreenStates.LOADING)
    val newsData : StateFlow<NewsScreenStates> =_newsData.asStateFlow()

    private val _connectivityStatus: MutableStateFlow<ConnectivityObserver.Status> =
        MutableStateFlow(ConnectivityObserver.Status.Available)
    val connectivityStatus: StateFlow<ConnectivityObserver.Status> =
        _connectivityStatus.asStateFlow()
    init {
        observeNetwork()
    }

    private fun observeNetwork() {
        viewModelScope.launch {
            connectivityObserver.observe().collect { status ->
                _connectivityStatus.value = status
                when (status) {
                    ConnectivityObserver.Status.Available -> {
                        getNews()
                        newsRepository.setPeriodicWorkRequest()
                    }
                    else -> {
                        _newsData.value = NewsScreenStates.LOADING
                        newsRepository.getNewsFromDb().collect { newsList ->
                            _newsData.value = NewsScreenStates.SUCCESS(newsList)
                        }
                    }
                }
            }
        }
    }

    private suspend fun getNews(){
        _newsData.value = NewsScreenStates.LOADING
        newsUseCase.getNews().collect{
            _newsData.value = NewsScreenStates.SUCCESS(it)
        }
    }
}