package com.example.mesiah.navigation // Ensure this matches your package structure

import androidx.compose.material.icons.Icons
// Import both Filled and Outlined icons you need
import androidx.compose.material.icons.filled.Chat // For Chatbot
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PictureAsPdf // For PDF RAG
import androidx.compose.material.icons.filled.PhotoCamera // For Camera feature
import androidx.compose.material.icons.outlined.Chat // For Chatbot
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PictureAsPdf // For PDF RAG
import androidx.compose.material.icons.outlined.PhotoCamera // For Camera feature
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector, // Icon when item is selected
    val unselectedIcon: ImageVector // Icon when item is not selected
) {
    object Home : BottomNavItem(
        route = "home",
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )

    // Changed Live to Camera based on app description
    object Camera : BottomNavItem( // Renamed from Live
        route = "camera", // Renamed route
        title = "Camera", // Renamed title
        selectedIcon = Icons.Filled.PhotoCamera, // Changed icon
        unselectedIcon = Icons.Outlined.PhotoCamera // Changed icon
    )

    // Changed RAG title slightly, updated icons
    object Rag : BottomNavItem(
        route = "rag",
        title = "PDF", // Changed title for clarity
        selectedIcon = Icons.Filled.PictureAsPdf, // Changed icon
        unselectedIcon = Icons.Outlined.PictureAsPdf // Changed icon
    )

    // Changed Account to Chat based on app description
    object Chat : BottomNavItem( // Renamed from Account
        route = "chat", // Renamed route
        title = "ChatBot", // Kept title
        selectedIcon = Icons.Filled.Chat, // Changed icon
        unselectedIcon = Icons.Outlined.Chat // Changed icon
    )
}

// IMPORTANT: Ensure you have the Material Icons Extended dependency in your app's build.gradle:
// implementation("androidx.compose.material:material-icons-extended:<compose_version>")
// Replace <compose_version> with your actual Compose library version.