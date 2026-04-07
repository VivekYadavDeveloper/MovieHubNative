package com.create.moviehubnative.Model

data class MovieResponse(
    val result: Boolean,
    val msg: String,
    val data: List<MovieDto>,
)

data class MovieDto(
    val id: String,
    val title: String,
    val year: Int,
    val genre: String?,
    val rating: Double?,
    val director: String?,
    val duration: String?,
    val poster: String?,
    val description: String?,
    val language: String?,
    val trending: Boolean,
    val parts: List<PartDto>,
)

data class PartDto(
    val partId: String,
    val title: String,
    val watched: Boolean,
    val duration: String,
)