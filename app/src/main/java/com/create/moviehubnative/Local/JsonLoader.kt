package com.create.moviehubnative.Local

import android.content.Context
import com.create.moviehubnative.Model.MovieResponse

import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class JsonLoader @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun loadMovies(): MovieResponse {
        return try {
            val json = context.assets.open("movies.json")
                .bufferedReader()
                .use { it.readText() }

            Gson().fromJson(json, MovieResponse::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            MovieResponse(result = false, msg = "Error loading JSON", data = emptyList())
        }
    }
}