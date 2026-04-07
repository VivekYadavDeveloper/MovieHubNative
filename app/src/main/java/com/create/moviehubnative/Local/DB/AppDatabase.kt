package com.create.moviehubnative.Local.DB

import androidx.room.Database
import androidx.room.RoomDatabase

import com.create.moviehubnative.Entities.PartEntity

import com.create.moviehubnative.Local.PartDao
import com.create.moviehubnative.data.local.dao.MovieDao
import com.create.moviehubnative.data.local.entity.MovieEntity


@Database(
    entities = [MovieEntity::class, PartEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun partDao(): PartDao
}