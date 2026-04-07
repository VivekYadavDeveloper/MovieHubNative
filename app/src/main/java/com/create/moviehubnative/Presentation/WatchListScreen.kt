package com.create.moviehubnative.Presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.create.moviehubnative.ViewModel.WatchlistViewModel
import com.create.moviehubnative.data.local.entity.MovieEntity


@Composable
fun WatchlistScreen(
    viewModel: WatchlistViewModel = hiltViewModel(),
    onMovieClick: (String) -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            Text(
                text = "My Watchlist",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color(0xFF1A1A1A)
                )
            )
            Text(
                text = "${state.movies.size} movies saved",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF757575),
                    fontSize = 12.sp
                )
            )
        }

        HorizontalDivider(Modifier, thickness = 1.dp, color = Color(0xFF303030))

        // Content
        if (state.isEmpty) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF373636)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(32.dp)
                ) {
                    Text(
                        "📽️",
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 64.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Your Watchlist is Empty",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF212121),
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Add movies to your watchlist to keep track of what you want to watch",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF757575),
                            fontSize = 12.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF373636)),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.movies) { movie ->
                    WatchlistMovieCard(
                        movie = movie,
                        onClick = { onMovieClick(movie.id) },
                        onRemove = { /* Handle remove */ }
                    )
                }
            }
        }
    }
}

@Composable
fun WatchlistMovieCard(
    movie: MovieEntity,
    onClick: () -> Unit,
    onRemove: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Poster Image
            if (!movie.poster.isNullOrEmpty()) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.poster)
                        .crossfade(true)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .width(70.dp)
                        .height(105.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(0xFF242424)),
                    contentScale = ContentScale.Crop,
                    loading = {
                        Box(
                            modifier = Modifier
                                .width(70.dp)
                                .height(105.dp)
                                .background(Color(0xFF413F3F)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.width(20.dp),
                                strokeWidth = 1.5.dp,
                                color = Color(0xFF2B2929)
                            )
                        }
                    },
                    error = {
                        Box(
                            modifier = Modifier
                                .width(70.dp)
                                .height(105.dp)
                                .background(Color(0xFF242222)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("N/A", color = Color(0xFF9E9E9E), fontSize = 10.sp)
                        }
                    }
                )
            } else {
                Box(
                    modifier = Modifier
                        .width(70.dp)
                        .height(105.dp)
                        .background(Color(0xFF353333))
                        .clip(RoundedCornerShape(2.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("N/A", color = Color(0xFF2B2A2A), fontSize = 10.sp)
                }
            }

            // Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.Top)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                // Title
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 25.sp,
                        color = Color(0xFF212121)
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // Year & Genre
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = movie.year.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            color = Color(0xFF757575)
                        )
                    )
                    if (!movie.genre.isNullOrEmpty()) {
                        Text(
                            text = "•",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                color = Color(0xFF757575)
                            )
                        )
                        Text(
                            text = movie.genre,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                color = Color(0xFF757575)
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Rating
                Text(
                    text = if (movie.rating != null && movie.rating > 0) {
                        "★ ${movie.rating}/10"
                    } else {
                        "Not rated"
                    },
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (movie.rating != null && movie.rating > 0) Color(0xFFF57C00) else Color(0xFF757575)
                    )
                )

                // Status
                if (movie.isCompleted) {
                    Text(
                        text = "✓ Completed",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 9.sp,
                            color = Color(0xFF1B5E20)
                        )
                    )
                }
            }

            // Remove Button
            IconButton(
                onClick = onRemove,
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp)
                    .align(Alignment.Top)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Remove from watchlist",
                    tint = Color(0xFFC62828),
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                )
            }
        }
    }
}