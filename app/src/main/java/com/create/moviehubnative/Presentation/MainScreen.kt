package com.create.moviehubnative.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.create.moviehubnative.Presentation.TabletScreen
import com.example.movieapp.ui.navigation.NavGraph


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    androidx.compose.material3.Scaffold(
        bottomBar = {
            if (screenWidth < 600) {
                BottomNavigationBar(navController)
            }
//            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Modifier.padding(innerPadding)

        if (screenWidth >= 600) {
            // ✅ Tablet UI (NO NAV GRAPH)
            TabletScreen()
        } else {
            // ✅ Phone UI (Navigation)
            NavGraph(navController)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },

            label = { Text("Browse") },
            selected = currentRoute == "movie_list",
            onClick = {
                navController.navigate("movie_list") {
                    popUpTo("movie_list") { inclusive = true }
                }
            }
        )

        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Favorite, contentDescription = "Watchlist") },
            label = { Text("Watchlist") },
            selected = currentRoute == "watchlist",
            onClick = {
                navController.navigate("watchlist") {
                    popUpTo("watchlist") { inclusive = true }
                }
            }
        )
    }
}