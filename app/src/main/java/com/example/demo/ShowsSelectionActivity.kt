/*
Written By: Ezzat Boukhary
Date: 12/17/2023
 */
package com.example.demo

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.adapter.OnItemClickListener
import com.example.demo.adapter.RecyclerViewAdapter
import com.example.demo.viewmodel.MovieViewModel

private const val SHOWS_NEEDED = 5

class ShowsSelectionActivity : AppCompatActivity(), OnItemClickListener {

    // Views
    private lateinit var recyclerView: RecyclerView
    private lateinit var moviesCountText: TextView
    private lateinit var continueButton: Button
    private lateinit var searchButton: ImageButton
    private lateinit var searchEditText: EditText
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shows_selection)

        // Instantiate views
        recyclerView = findViewById(R.id.recyclerView_shows)
        moviesCountText = findViewById(R.id.shows_selected_textview)
        continueButton = findViewById(R.id.continue_button_shows)
        searchButton = findViewById(R.id.shows_search_button)
        searchEditText = findViewById(R.id.shows_search_et)

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.fetchShows(1)

        // Get list of movies
        movieViewModel.movies.observe(this) { moviesList ->

            val recyclerViewAdapter = RecyclerViewAdapter(moviesList)
            recyclerViewAdapter.setOnItemClickListener(this)

            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = recyclerViewAdapter
            val layoutManager = GridLayoutManager(this, 2)

            recyclerView.layoutManager = layoutManager

        }

        // Get borders for selected movies
        movieViewModel.borderImgVisibilityList.observe(this) { visibilityList ->
            visibilityList.forEachIndexed { index, isVisible ->
                // Update borderImg visibility for each movie
                recyclerView.findViewHolderForAdapterPosition(index)?.itemView?.findViewById<ImageView>(R.id.selected_border_imageview)?.isVisible = isVisible
            }
        }

        // Get borders for selected movies
        movieViewModel.tickImgVisibilityList.observe(this) { visibilityList ->
            visibilityList.forEachIndexed { index, isVisible ->

                // Update tickImg visibility for each movie
                recyclerView.findViewHolderForAdapterPosition(index)?.itemView?.findViewById<ImageView>(R.id.selected_checkmark_imageview)?.isVisible = isVisible
            }
        }

        // Get clickedMovies from viewModel
        movieViewModel.clickedMovies.observe(this) { clickedMovies ->

            // update "0/5 selected" text to reflect actual number selected
            moviesCountText.text = getString(R.string.movies_selected_text, clickedMovies.size)

            // Check if Continue button should turn or or not
            if (clickedMovies.size == SHOWS_NEEDED) { // enough genres selected -> enable continue btn
                continueButton.isEnabled = true
                continueButton.setTextColor(Color.BLACK)
                continueButton.setBackgroundColor(resources.getColor(R.color.primary_yellow))

            } else { // Not enough genres selected -> Disable continue btn
                continueButton.isEnabled = false
                continueButton.setTextColor(resources.getColor(R.color.secondary_grey))
                continueButton.setBackgroundColor(resources.getColor(R.color.disabled_grey))
            }
        }

        // Continue button -> Go to next activity
        continueButton.setOnClickListener {
            startActivity(Intent(applicationContext, GuidelinesActivity::class.java))
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
            if (clickedMovies.size == SHOWS_NEEDED) { // enough genres selected -> enable continue btn
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