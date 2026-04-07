package com.create.moviehubnative.Repository



import com.create.moviehubnative.Entities.PartEntity
import com.create.moviehubnative.Local.JsonLoader

import com.create.moviehubnative.Local.PartDao
import com.create.moviehubnative.data.local.dao.MovieDao
import com.create.moviehubnative.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieDao: MovieDao,
    private val partDao: PartDao,
    private val jsonLoader: JsonLoader,
) {

    suspend fun initializeIfFirstLaunch() {
        try {
            val existingMovies = movieDao.getMovies().first()
            if (existingMovies.isNotEmpty()) return

            val response = jsonLoader.loadMovies()
            if (!response.result) return

            // Insert movies
            val movies = response.data.map {
                MovieEntity(
                    id = it.id,
                    title = it.title,
                    year = it.year,
                    genre = it.genre,
                    rating = it.rating,
                    director = it.director,
                    duration = it.duration,
                    poster = it.poster,
                    description = it.description,
                    language = it.language,
                    trending = it.trending
                )
            }
            movieDao.insertMovies(movies)

            // Insert parts
            val parts = response.data.flatMap { movie ->
                movie.parts.map {
                    PartEntity(
                        partId = it.partId,
                        movieId = movie.id,
                        title = it.title,
                        watched = it.watched,
                        duration = it.duration
                    )
                }
            }
            if (parts.isNotEmpty()) {
                partDao.insertParts(parts)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getMovies() = movieDao.getMovies()

    fun getMovieById(id: String) = movieDao.getMovieById(id)

    fun getWatchlist() = movieDao.getWatchlist()

    fun getTrendingMovies() = movieDao.getTrendingMovies()

    fun getPartsByMovieId(movieId: String) = partDao.getPartsByMovieId(movieId)

    suspend fun toggleWatchlist(movie: MovieEntity) {
        movieDao.updateMovie(movie.copy(isWatchlisted = !movie.isWatchlisted))
    }

    suspend fun updatePart(part: PartEntity) {
        partDao.updatePart(part.copy(watched = !part.watched))
    }

    suspend fun markCompleted(movie: MovieEntity) {
        movieDao.updateMovie(movie.copy(isCompleted = true))
    }
}