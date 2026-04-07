package com.create.moviehubnative.Presentation



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.create.moviehubnative.ViewModel.MovieListViewModel
import com.create.moviehubnative.data.local.entity.MovieEntity


@Composable
fun TabletScreen(
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var selectedMovie by remember { mutableStateOf<MovieEntity?>(null) }

    Row(modifier = Modifier.fillMaxSize()) {
        // Left side - Movie List (40%)
        LazyColumn(modifier = Modifier.width(280.dp)) {
            items(state.filteredMovies) { movie ->
                Text(
                    text = movie.title,
                    modifier = Modifier
                        .clickable { selectedMovie = movie }
                        .padding(16.dp)
                )
            }
        }

        // Right side - Detail (60%)
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxSize()) {
            selectedMovie?.let { movie ->
                Text("Detail for: ${movie.title}", modifier = Modifier.align(Alignment.Center))
            } ?: Text("Select a movie", modifier = Modifier.align(Alignment.Center))
        }
    }
}