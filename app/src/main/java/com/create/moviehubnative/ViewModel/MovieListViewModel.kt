package com.create.moviehubnative.ViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.create.moviehubnative.Repository.MovieRepository
import com.create.moviehubnative.data.local.entity.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.filter

data class MovieListUiState(
    val movies: List<MovieEntity> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedGenre: String = "All",
    val filteredMovies: List<MovieEntity> = emptyList(),
)

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _selectedGenre = MutableStateFlow("All")
    private val _isLoading = MutableStateFlow(true)

    val uiState: StateFlow<MovieListUiState> = combine(
        repository.getMovies(),
        _searchQuery,
        _selectedGenre,
        _isLoading
    ) { movies, query, genre, isLoading ->
        val filtered = movies.filter { movie ->
            val matchesSearch = movie.title.contains(query, ignoreCase = true)
            val matchesGenre = genre == "All" || movie.genre == genre
            matchesSearch && matchesGenre
        }

        MovieListUiState(
            movies = movies,
            isLoading = isLoading,
            searchQuery = query,
            selectedGenre = genre,
            filteredMovies = filtered
        )
    }.stateIn(
        viewModelScope,
        kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
        MovieListUiState()
    )

    init {
        viewModelScope.launch {
            repository.initializeIfFirstLaunch()
            _isLoading.value = false
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onGenreChange(genre: String) {
        _selectedGenre.value = genre
    }

    fun getAvailableGenres(movies: List<MovieEntity>): List<String> {
        return listOf("All") + movies.mapNotNull { it.genre }.distinct().sorted()
    }
}