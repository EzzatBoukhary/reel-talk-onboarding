/*
Written By: Ezzat Boukhary
Date: 12/15/2023
 */
package com.example.demo

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


private const val API_KEY = ""
private const val API_HOST = ""

interface MovieService {

    @GET("titles")
    suspend fun getTitles(
        @Header("X-RapidAPI-Key") apiKey: String = API_KEY, // required
        @Header("X-RapidAPI-Host") apiHost: String = API_HOST, // required
        @Query("titleType") type: String, // filter for movies
        @Query("year") year: Int = 2023,
        @Query("page") page: Int
    ): ApiResponse

    @GET("/titles/search/title/{title}")
    suspend fun getMovie(
        @Path("title") title: String,
        @Header("X-RapidAPI-Key") apiKey: String = API_KEY, // required
        @Header("X-RapidAPI-Host") apiHost: String = API_HOST, // required
        @Query("titleType") type: String = "movie", // filter for movies
        @Query("exact") exact: Boolean = true
    ): ApiResponse

    @GET("/titles/utils/genres")
    suspend fun getGenres(
        @Header("X-RapidAPI-Key") apiKey: String = API_KEY, // required
        @Header("X-RapidAPI-Host") apiHost: String = API_HOST, // required
    ): GenresResponse
}


// JSON data to Data classes:
data class ApiResponse(
    val page: Int,
    val next: String?,
    val entries: Int,
    val results: List<MovieResponse>
)

data class MovieResponse(
    val _id: String,
    val id: String,
    val titleText: TitleText,
    val releaseYear: YearRange,
    val primaryImage: Image?
)

data class GenresResponse(
    val results: List<String?>
)

data class TitleText(
    val text: String
)

data class YearRange(
    val year: Int,
    val endYear: Int
)

data class Image(
    val url: String?
)