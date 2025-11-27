package com.app.examen_tc2007b.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.examen_tc2007b.presentation.screens.date.DateScreen
import com.app.examen_tc2007b.presentation.screens.home.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Date : Screen("date")
}

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Date.route) {
            DateScreen()
        }
    }
}
