package com.example.demo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.adapter.OnItemClickListener
import com.example.demo.adapter.RecyclerViewAdapter
import com.example.demo.viewmodel.MovieViewModel

private const val MOVIES_NEEDED = 5

class MovieSelectionActivity : AppCompatActivity(), OnItemClickListener {

    // Views
    private lateinit var recyclerView: RecyclerView
    private lateinit var moviesCountText: TextView
    private lateinit var continueButton: Button
    private lateinit var searchButton: ImageButton
    private lateinit var searchEditText: EditText
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_selection)

        // Instantiate views
        recyclerView = findViewById(R.id.moviesRecyclerView)
        moviesCountText = findViewById(R.id.movies_selected_textview)
        continueButton = findViewById(R.id.continue_button_movies)
        searchButton = findViewById(R.id.floating_search_button)
        searchEditText = findViewById(R.id.floating_search_et)

        var page: Int = 1;

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.fetchMovies(page)

        searchButton.setOnClickListener {
            val searchText = searchEditText.text.toString()

            // if search button was clicked with text then search the movie
            if (searchText.isNotEmpty()) {

                // Fetch movie in API
                movieViewModel.fetchMovie(searchText)

                // Observe found movie and display it
                movieViewModel.foundMovie.observe(this) { movie ->
                    if (movie != null) {

                        val recyclerViewAdapter = RecyclerViewAdapter(movie)
                        recyclerViewAdapter.setOnItemClickListener(this)

                        recyclerView.setHasFixedSize(true)
                        recyclerView.adapter = recyclerViewAdapter
                        val layoutManager = LinearLayoutManager(this)

                        recyclerView.layoutManager = layoutManager
                    }
                    else {
                        setAdapterToAll()
                    }
                }
            }
            else {
                page += 1
                movieViewModel.fetchMovies(page)
                setAdapterToAll()
            }
        }

        setAdapterToAll()
        updateUI()

        // Continue button -> Go to next activity
        continueButton.setOnClickListener {
            startActivity(Intent(applicationContext, ShowsSelectionActivity::class.java))
            finish()
        }



    }

    override fun onItemClick(clickedButton: ImageButton, position: Int) {
        movieViewModel.movies.observe(this) { moviesList ->
            val added = movieViewModel.onMovieClicked(moviesList[position])

            // Update UI with new button and other info
            updateUI()
        }
    }

    // Fills the recycler view with a list of movies
    private fun setAdapterToAll() {
        movieViewModel.movies.observe(this) { moviesList ->
            val recyclerViewAdapter = RecyclerViewAdapter(moviesList)
            recyclerViewAdapter.setOnItemClickListener(this)

            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = recyclerViewAdapter
            val layoutManager = GridLayoutManager(this, 2)

            recyclerView.layoutManager = layoutManager
        }
    }

    // Update UI for every genre and for any changes
    private fun updateUI() {

        movieViewModel.borderImgVisibilityList.observe(this) { visibilityList ->
            visibilityList.forEachIndexed { index, isVisible ->
                // Update borderImg visibility for each movie
                recyclerView.findViewHolderForAdapterPosition(index)?.itemView?.findViewById<ImageView>(R.id.selected_border_imageview)?.isVisible = isVisible
            }
        }

        movieViewModel.tickImgVisibilityList.observe(this) { visibilityList ->
            visibilityList.forEachIndexed { index, isVisible ->
                // Update tickImg visibility for each movie
                recyclerView.findViewHolderForAdapterPosition(index)?.itemView?.findViewById<ImageView>(R.id.selected_checkmark_imageview)?.isVisible = isVisible
            }
        }

        movieViewModel.clickedMovies.observe(this) { clickedMovies ->

            // update "0/5 selected" text to reflect actual number selected
            moviesCountText.text = getString(R.string.movies_selected_text, clickedMovies.size)

            // Check if Continue button should turn or or not
            if (clickedMovies.size == MOVIES_NEEDED) { // enough genres selected -> enable continue btn
                continueButton.isEnabled = true
                continueButton.setTextColor(Color.BLACK)
                continueButton.setBackgroundColor(resources.getColor(R.color.primary_yellow))
            } else { // Not enough genres selected -> Disable continue btn
                continueButton.isEnabled = false
                continueButton.setTextColor(resources.getColor(R.color.secondary_grey))
                continueButton.setBackgroundColor(resources.getColor(R.color.disabled_grey))
            }
        }

    }
}