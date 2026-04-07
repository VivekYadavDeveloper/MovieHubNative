package com.create.moviehubnative.Presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.create.moviehubnative.ViewModel.MovieDetailViewModel
import com.create.moviehubnative.data.local.entity.MovieEntity


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.movie?.title ?: "Movie Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (state.movie == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Movie not found")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    val movie = state.movie!!

                    // Poster
                    if (!movie.poster.isNullOrEmpty()) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(movie.poster)
                                .crossfade(true) // Smooth transition on load
                                .diskCachePolicy(CachePolicy.ENABLED) // Enable caching
                                .memoryCachePolicy(CachePolicy.ENABLED)
                                .build(),
                            contentDescription = movie.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .clip(RoundedCornerShape(8.dp)), // Apply shape here
                            contentScale = ContentScale.Crop, // Crop to fill the 300.dp height
                            onSuccess = { /* Handle success if needed */ },
                            onError = { /* Handle error if needed */ }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Info
                    MovieInfoSection(movie)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Description
                    if (!movie.description.isNullOrEmpty()) {
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = movie.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // Watchlist Button
                    Button(
                        onClick = { viewModel.toggleWatchlist(movie) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            if (movie.isWatchlisted) "Remove from Watchlist" else "Add to Watchlist"
                        )
                    }
                }

                // Parts Section
                if (state.parts.isEmpty()) {
                    item {
                        Text(
                            text = "No episodes available",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                } else {
                    item {
                        Text(
                            text = "Episodes (${state.parts.count { it.watched }}/${state.parts.size} watched)",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    items(state.parts) { part ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = part.watched,
                                    onCheckedChange = {
                                        viewModel.togglePartWatched(part)
                                    }
                                )

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = part.title,
                                        style = MaterialTheme.typography.bodyMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "Duration: ${part.duration}",
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }
                    }

                    // Complete Movie Button
                    if (state.allPartsWatched && !state.movie!!.isCompleted) {
                        item {
                            Button(
                                onClick = { viewModel.markMovieCompleted(state.movie!!) },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Mark as Completed")
                            }
                        }
                    }

                    if (state.movie!!.isCompleted) {
                        item {
                            Text(
                                text = "✓ All episodes watched - Completed!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieInfoSection(movie: MovieEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MovieInfoRow("Year", movie.year.toString())
            if (!movie.genre.isNullOrEmpty()) {
                MovieInfoRow("Genre", movie.genre)
            }
            if (!movie.director.isNullOrEmpty()) {
                MovieInfoRow("Director", movie.director)
            }
            if (movie.rating != null && movie.rating > 0) {
                MovieInfoRow("Rating", "⭐ ${movie.rating}/10")
            } else {
                MovieInfoRow("Rating", "Not rated")
            }
            if (!movie.language.isNullOrEmpty()) {
                MovieInfoRow("Language", movie.language)
            }
        }
    }
}

@Composable
fun MovieInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}