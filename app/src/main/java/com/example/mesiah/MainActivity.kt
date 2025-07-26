package com.example.mesiah // Ensure this matches your package structure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar // Keep this import
import androidx.compose.material3.NavigationBarItem // Keep this import
import androidx.compose.material3.NavigationBarItemDefaults // Import for colors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController // Use specific type
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mesiah.navigation.AppNavHost // Your NavHost composable
import com.example.mesiah.navigation.BottomNavItem // Your updated NavItem class
import com.example.mesiah.ui.theme.MesiahTheme // Your app's theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MesiahTheme { // Apply your app's theme
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    // Use the updated BottomNavItem objects
    val navItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Camera, // Updated name
        BottomNavItem.Rag,
        BottomNavItem.Chat    // Updated name
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        // Consider a more fitting title for your app, e.g., "Study Buddy AI"
                        text = "VidyaDaan❤️",
                        fontWeight = FontWeight.Bold // Optional: Keep bold or adjust style
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            // Use the refactored BottomNavigationBar composable
            BottomNavigationBar(navController = navController, items = navItems)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Apply padding from Scaffold
        ) {
            // Ensure your AppNavHost is set up correctly with the new routes ("camera", "chat")
            AppNavHost(navController = navController)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, items: List<BottomNavItem>) {
    NavigationBar(
        // You can customize the container color if needed
        // containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            // Check if the current destination route is part of the hierarchy of the item's route
            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

            NavigationBarItem(
                icon = {
                    Icon(
                        // Use selectedIcon or unselectedIcon based on the 'selected' state
                        imageVector = if (selected) screen.selectedIcon else screen.unselectedIcon,
                        contentDescription = screen.title
                    )
                },
                label = { Text(screen.title) },
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                // Customize the colors for a more beautiful look
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary, // Color for the selected icon
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant, // Color for unselected icons (often slightly muted)
                    selectedTextColor = MaterialTheme.colorScheme.primary, // Color for the selected text label
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant, // Color for unselected text labels
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer // Background color of the selection indicator pill
                ),
                // Optional: Control if the label is always shown or only when selected
                alwaysShowLabel = true // Keep true to always show labels, false to hide unselected
            )
        }
    }
}