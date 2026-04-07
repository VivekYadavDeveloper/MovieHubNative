package com.create.moviehubnative.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val year: Int,
    val genre: String? = null,
    val rating: Double? = null,
    val director: String? = null,
    val duration: String? = null,
    val poster: String? = null,
    val description: String? = null,
    val language: String? = null,
    val trending: Boolean = false,
    val isWatchlisted: Boolean = false,
    val isCompleted: Boolean = false,
)