package com.example.newsfeed.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsfeed.presentation.SharedViewModel
import com.example.newsfeed.presentation.screens.detailscreen.DetailScreen
import com.example.newsfeed.presentation.screens.mainscreen.NewsFeedScreen

@Composable
fun MainNavigation(
    sharedViewModel: SharedViewModel= hiltViewModel()
){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationScreens.HomeScreen.route) {
        composable(route = NavigationScreens.HomeScreen.route){
            NewsFeedScreen(navController,sharedViewModel)
        }

        composable(route = NavigationScreens.DetailScreen.route){
            DetailScreen(sharedViewModel){
                navController.popBackStack()
            }
        }

    }
}