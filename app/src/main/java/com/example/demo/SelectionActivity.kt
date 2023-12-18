/*
Written By: Ezzat Boukhary
Date: 12/16/2023
 */
package com.example.demo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setMargins
import androidx.lifecycle.ViewModelProvider
import com.example.demo.viewmodel.MovieViewModel
import com.google.android.flexbox.FlexboxLayout


private const val GENRES_NEEDED = 3

class SelectionActivity : AppCompatActivity() {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var continueButton: Button
    private lateinit var genresCountText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.fetchGenres()

        // FlexboxLayout that will hold all the genre buttons
        val buttonsLayout = findViewById<FlexboxLayout>(R.id.genresBoxLayout)
        continueButton = findViewById(R.id.continue_button_genres)
        genresCountText = findViewById(R.id.genres_selected_textview)
        updateUI()
        // Continue button -> Go to next activity
        continueButton.setOnClickListener {
            startActivity(Intent(applicationContext, MovieSelectionActivity::class.java))
            finish()
        }

        // Create a mutable list to track clicked buttons
        val clickedButtons = mutableListOf<Button>()

        movieViewModel.genres.observe(this) { genresList ->
            for (genre in genresList) {

                val button = Button(this)

                // Customize genre button
                button.text = genre
                button.setTextColor(Color.WHITE)
                button.setBackgroundResource(R.drawable.genre_button_style)
                button.isAllCaps = false
                button.setPadding(55, 5, 55, 5)

                // Add click listener
                button.setOnClickListener {
                    val added = movieViewModel.onGenreClicked(it as Button)

                    // Update UI with new button and other info
                    updateUI()
                }

                // Add the button to the existing LinearLayout
                buttonsLayout.addView(button)

                // Set button layout parameters
                val params = button.layoutParams as FlexboxLayout.LayoutParams
                params.setMargins(16)
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                button.layoutParams = params

            }
        }

    }

    // Update UI for every genre and for any changes
    fun updateUI() {

        movieViewModel.clickedGenres.observe(this) { clickedGenres ->
            Log.d("TAG", "updateUI: $clickedGenres")

            // update "0/3 selected" text to reflect actual number selected
            genresCountText.text = getString(R.string.genres_selected_text, clickedGenres.size)

            // Check if Continue button should turn or or not
            if (clickedGenres.size == GENRES_NEEDED) { // enough genres selected -> enable continue btn
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