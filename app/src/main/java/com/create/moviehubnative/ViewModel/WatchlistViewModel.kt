package com.create.moviehubnative.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.create.moviehubnative.Repository.MovieRepository
import com.create.moviehubnative.data.local.entity.MovieEntity

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WatchlistUiState(
    val movies: List<MovieEntity> = emptyList(),
    val isEmpty: Boolean = true,
    val isLoading: Boolean = false,
)

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(WatchlistUiState())
    val uiState: StateFlow<WatchlistUiState> = _uiState.asStateFlow()

    init {
        loadWatchlist()
    }

    private fun loadWatchlist() {
        viewModelScope.launch {
            repository.getWatchlist().collect { movies ->
                _uiState.value = WatchlistUiState(
                    movies = movies,
                    isEmpty = movies.isEmpty(),
                    isLoading = false
                )
            }
        }
    }

    private var lastRemoved: MovieEntity? = null

    fun removeFromWatchlist(movieId: String) {
        viewModelScope.launch {
            val movie = _uiState.value.movies.find { it.id == movieId }
            if (movie != null) {
                lastRemoved = movie
                repository.toggleWatchlist(movie)
            }
        }
    }

    fun undoRemove() {
        viewModelScope.launch {
            lastRemoved?.let {
                repository.toggleWatchlist(it)
            }
        }
    }
}