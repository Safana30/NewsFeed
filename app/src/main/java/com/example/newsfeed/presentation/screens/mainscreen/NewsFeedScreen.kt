package com.example.newsfeed.presentation.screens.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsfeed.BuildConfig
import com.example.newsfeed.R
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

    val appName = stringResource(id = R.string.app_name)
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BaseToolbar(title = appName)
        },
        bottomBar = {
            if (BuildConfig.SHOW_ADS) {
                AdPlaceholder()
            }
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
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = errorText, color = Color.Red)
                    }
                }

                is NewsScreenStates.SUCCESS -> {
                    val articles = if (BuildConfig.SHOW_ADS) {
                        newsState.article?.take(10) ?: emptyList()
                    } else {
                        newsState.article ?: emptyList()
                    }
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

@Composable
fun AdPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .navigationBarsPadding()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Ad Icon",
                tint = Color.DarkGray
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Advertisement",
                color = Color.DarkGray,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}