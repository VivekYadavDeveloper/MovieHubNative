package com.create.moviehubnative.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.create.moviehubnative.data.local.entity.MovieEntity

@Entity(
    tableName = "parts",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["movieId"])  // ✅ Index add kiya
    ]
)
data class PartEntity(
    @PrimaryKey
    val partId: String,
    val movieId: String,
    val title: String,
    val watched: Boolean = false,
    val duration: String = "0"
)