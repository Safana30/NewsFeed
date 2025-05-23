package com.example.newsfeed.presentation.screens.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsfeed.presentation.SharedViewModel
import com.example.newsfeed.presentation.components.BaseToolbar
import com.example.newsfeed.presentation.navigation.NavigationScreens
import com.example.newsfeed.presentation.viewmodel.NewsFeedViewModel
import com.example.newsfeed.utils.ConnectivityObserver

@Composable
fun NewsFeedScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    viewModel: NewsFeedViewModel = hiltViewModel(),

) {
    val newsState = viewModel.newsData.collectAsState().value
    val connectivityStatus = viewModel.connectivityStatus.collectAsState().value

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BaseToolbar(title = "NewsFeed")
        }
    ) {paddingValue->
        Box (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ){
            val errorText = if (connectivityStatus == ConnectivityObserver.Status.Unavailable ||
                connectivityStatus == ConnectivityObserver.Status.Lost) {
                "No Internet"
            } else {
                "Error in Loading News"
            }
            when (newsState) {
                is NewsScreenStates.LOADING -> {
                    // Show loading indicator
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is NewsScreenStates.ERROR -> {
                    // Show error message
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = errorText, color = Color.Red)
                    }
                }

                is NewsScreenStates.SUCCESS -> {
                    val articles = newsState.article ?: emptyList()
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(articles) { article ->
                            NewsFeedCard(article, onClick = {
                                navController.navigate(NavigationScreens.DetailScreen.route)
                                sharedViewModel.setArticle(article)
                            })
                        }
                    }
                }
            }

        }
    }

}