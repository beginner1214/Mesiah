package com.example.mesiah.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.mesiah.ui.theme.screens.AccountScreen
import com.example.mesiah.ui.theme.screens.HomeScreen
import com.example.mesiah.ui.theme.screens.LiveScreen
import com.example.mesiah.ui.theme.screens.RagScreen
import com.example.mesiah.navigation.BottomNavItem

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Home.route) {
            HomeScreen()
        }
        composable(BottomNavItem.Camera.route) {
            LiveScreen()
        }
        composable(BottomNavItem.Rag.route) {
            RagScreen()
        }
        composable(BottomNavItem.Chat.route) {
            AccountScreen()
        }
    }
}