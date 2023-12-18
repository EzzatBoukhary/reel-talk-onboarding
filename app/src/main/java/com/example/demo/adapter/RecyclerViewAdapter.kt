/*
Written By: Ezzat Boukhary
Date: 12/17/2023
 */
package com.example.demo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.MovieResponse
import com.example.demo.R
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(private var movies: List<MovieResponse>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private var onItemClickListener: OnItemClickListener? = null


    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]

        // set thumbnail
        Picasso.get()
            .load(movie.primaryImage?.url)
            .placeholder(android.R.drawable.stat_sys_warning) // placeholder image
            .error(android.R.drawable.stat_notify_error) // error image
            .fit()
            .centerCrop()
            .into(holder.imageButton);

        // Get release window
        val releaseWindow: String = when {
            (movie.releaseYear.year == movie.releaseYear.endYear || movie.releaseYear.endYear == 0) -> "(${movie.releaseYear.year})"
            else -> "(${movie.releaseYear.year} - ${movie.releaseYear.endYear})"
        }

        // Set title
        holder.itemTextView.text = "${movie.titleText.text} $releaseWindow"

        // set custom click listener to imageButton
        holder.imageButton.setOnClickListener {
            Log.d("TAG", "onBindViewHolder: clicked ${movie.titleText.text}")
            onItemClickListener?.onItemClick(it as ImageButton, position)
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Defining views in item layout
        val imageButton = itemView.findViewById<ImageButton>(R.id.movie_item_imagebutton)
        val itemTextView = itemView.findViewById<TextView>(R.id.movie_item_textview)

        }
    }
