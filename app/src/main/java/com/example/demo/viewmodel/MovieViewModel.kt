/*
Written By: Ezzat Boukhary
Date: 12/17/2023
 */
package com.example.demo.viewmodel

import android.graphics.Color
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.MovieResponse
import com.example.demo.R
import com.example.demo.repository.Repository
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val repository = Repository()

    private val _movies = MutableLiveData<List<MovieResponse>>()
    val movies: LiveData<List<MovieResponse>> get() = _movies

    private val _foundMovie = MutableLiveData<List<MovieResponse>>()
    val foundMovie: LiveData<List<MovieResponse>> get() = _foundMovie

    private val _genres = MutableLiveData<List<String>>()
    val genres: LiveData<List<String>> get() = _genres

    private val _clickedMovies = MutableLiveData<MutableList<MovieResponse>>(mutableListOf())
    val clickedMovies: LiveData<MutableList<MovieResponse>> get() = _clickedMovies

    private val _clickedGenres = MutableLiveData<MutableList<Button>>(mutableListOf())
    val clickedGenres: LiveData<MutableList<Button>> get() = _clickedGenres


    // List of booleans for UI updates
    private val _borderImgVisibilityList = MutableLiveData<List<Boolean>>(emptyList())
    val borderImgVisibilityList: LiveData<List<Boolean>> get() = _borderImgVisibilityList

    private val _tickImgVisibilityList = MutableLiveData<List<Boolean>>(emptyList())
    val tickImgVisibilityList: LiveData<List<Boolean>> get() = _tickImgVisibilityList

    init {

    }

    fun fetchMovies(page: Int) {
        viewModelScope.launch {
            _movies.value = repository.getMovies(page)
        }
    }

    fun fetchShows(page: Int) {
        viewModelScope.launch {
            _movies.value = repository.getShows(page)
        }
    }

    fun fetchMovie(movieName: String) {
        viewModelScope.launch {
            _foundMovie.value = repository.getMovie(movieName).takeIf { it.isNotEmpty() }

        }
    }

    fun fetchGenres() {
        viewModelScope.launch {
            _genres.value = repository.getGenres()
        }
    }

    fun onMovieClicked(movie: MovieResponse) {

        if (_clickedMovies.value?.contains(movie) == true) {
            _clickedMovies.value?.remove(movie)


        } else {
            if ((_clickedMovies.value?.size ?: 0) < MOVIES_NEEDED) {
                _clickedMovies.value?.add(movie)

            }
        }

        // Update the LiveData with the new list of clicked movies
        _clickedMovies.value = _clickedMovies.value
        updateUiVisibilityList()
    }


    fun onGenreClicked(genre: Button) {

        if (_clickedGenres.value?.contains(genre) == true) {
            _clickedGenres.value?.remove(genre)
            genre.setTextColor(Color.WHITE)
            genre.setBackgroundResource(R.drawable.genre_button_style)


        } else {
            if ((_clickedGenres.value?.size ?: 0) < GENRES_NEEDED) {
                _clickedGenres.value?.add(genre)
                // set button to pressed state
                genre.setTextColor(Color.BLACK)
                genre.setBackgroundResource(R.drawable.genre_button_pressed)

            }
        }

        // Update the LiveData with the new list of clicked genres
        _clickedGenres.value = _clickedGenres.value

    }


    private fun updateUiVisibilityList() {
        val movies = _movies.value.orEmpty()
        val clickedMovies = _clickedMovies.value.orEmpty()

        // Initialize visibility lists with default values
        val borderImgVisibilityList = MutableList(movies.size) { false }
        val tickImgVisibilityList = MutableList(movies.size) { false }

        // Update visibility lists based on clicked movies
        clickedMovies.forEach { clickedMovie ->
            val index = movies.indexOf(clickedMovie)
            if (index != -1) {
                borderImgVisibilityList[index] = true
                tickImgVisibilityList[index] = true
            }
        }

        // Update LiveData with the visibility lists
        _borderImgVisibilityList.value = borderImgVisibilityList
        _tickImgVisibilityList.value = tickImgVisibilityList
    }

    companion object {
        private const val MOVIES_NEEDED = 5
        private const val GENRES_NEEDED = 3
    }


}
