package com.example.movieapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.create.moviehubnative.Presentation.MovieDetailScreen

import com.create.moviehubnative.Presentation.WatchlistScreen
import com.create.moviehubnative.ui.screen.MovieListScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "movie_list"
    ) {
        composable("movie_list") {
            MovieListScreen(
                onMovieClick = { movieId ->
                    navController.navigate("movie_detail/$movieId")
                }
            )
        }

        composable("watchlist") {
            WatchlistScreen(
                onMovieClick = { movieId ->
                    navController.navigate("movie_detail/$movieId")
                }
            )
        }

        composable("movie_detail/{movieId}") {
            MovieDetailScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}