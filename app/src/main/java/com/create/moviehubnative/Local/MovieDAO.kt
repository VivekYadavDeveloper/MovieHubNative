package com.create.moviehubnative.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.create.moviehubnative.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY title ASC")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id: String): Flow<MovieEntity?>

    @Query("SELECT * FROM movies WHERE isWatchlisted = 1 ORDER BY title ASC")
    fun getWatchlist(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE trending = 1 ORDER BY title ASC")
    fun getTrendingMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Update
    suspend fun updateMovie(movie: MovieEntity)

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()
}