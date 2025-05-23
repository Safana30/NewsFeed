package com.example.newsfeed.presentation.navigation

sealed class NavigationScreens(val route:String) {
    data object HomeScreen: NavigationScreens("homescreen")
    data object DetailScreen: NavigationScreens("details")
}