/*
Written By: Ezzat Boukhary
Date: 12/17/2023
 */
package com.example.demo.repository

import android.util.Log
import com.example.demo.MovieResponse
import com.example.demo.MovieService
import com.example.demo.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {

    // Create/Get instance of retrofit service
    private val movieAPI = RetrofitInstance
        .getRetrofitInstance()
        .create(MovieService::class.java)


    // Returns a list of movies fetched from the API
    suspend fun getMovies(pageRequested: Int): List<MovieResponse> {
        return withContext(Dispatchers.IO) {
            val response = movieAPI.getTitles(type = "movie", page = pageRequested)
            response.results
        }
    }

    // Returns a movie fetched from the API
    suspend fun getMovie(movieName: String): List<MovieResponse> {
        return withContext(Dispatchers.IO) {
            val response = movieAPI.getMovie(title = movieName)
            response.results
        }
    }

    // Returns a list of movies fetched from the API
    suspend fun getShows(pageRequested: Int): List<MovieResponse> {
        return withContext(Dispatchers.IO) {
            val response = movieAPI.getTitles(type = "tvSeries", page = pageRequested)
            response.results
        }
    }

    // Returns a list of genres fetched from the API
    suspend fun getGenres(): List<String> {
        return withContext(Dispatchers.IO) {
            val response = movieAPI.getGenres()
            response.results.filterNotNull()
        }
    }
}
