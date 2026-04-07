package com.create.moviehubnative.ViewModel


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.create.moviehubnative.Entities.PartEntity
import com.create.moviehubnative.Repository.MovieRepository
import com.create.moviehubnative.data.local.entity.MovieEntity

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MovieDetailUiState(
    val movie: MovieEntity? = null,
    val parts: List<PartEntity> = emptyList(),
    val allPartsWatched: Boolean = false,
    val isLoading: Boolean = true,
)

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val movieId: String = checkNotNull(savedStateHandle["movieId"])

    val uiState: StateFlow<MovieDetailUiState> = combine(
        repository.getMovieById(movieId),
        repository.getPartsByMovieId(movieId)
    ) { movie, parts ->
        val allWatched = parts.isNotEmpty() && parts.all { it.watched }

        MovieDetailUiState(
            movie = movie,
            parts = parts,
            allPartsWatched = allWatched,
            isLoading = false
        )
    }.stateIn(
        viewModelScope,
        kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        MovieDetailUiState(isLoading = true)
    )

    fun toggleWatchlist(movie: MovieEntity) {
        viewModelScope.launch {
            repository.toggleWatchlist(movie)
        }
    }

    fun togglePartWatched(part: PartEntity) {
        viewModelScope.launch {
            repository.updatePart(part)
        }
    }

    fun markMovieCompleted(movie: MovieEntity) {
        viewModelScope.launch {
            repository.markCompleted(movie)
        }
    }
}