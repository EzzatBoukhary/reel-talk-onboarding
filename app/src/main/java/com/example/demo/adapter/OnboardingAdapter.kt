/*
Written By: Ezzat Boukhary
Date: 12/15/2023
 */
package com.example.demo.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.OnboardingItem
import com.example.demo.R

class OnboardingAdapter(private val onboardingItems: List<OnboardingItem>, private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    interface OnItemClickListener {
        fun onNextButtonClick()
    }

    inner class OnboardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {

            // Set click listener for the nextButton
            itemView.findViewById<Button>(R.id.nextButton).setOnClickListener {
                onItemClickListener.onNextButtonClick()
            }

            val loginTextView: TextView = itemView.findViewById(R.id.loginTextView)

            // Create a SpannableString
            val spannableString = SpannableString(loginTextView.text)

            // Set the color for "Log in" to yellow
            val yellowSpan = ForegroundColorSpan(Color.parseColor("#FFA724"))
            spannableString.setSpan(yellowSpan, spannableString.length - 6, spannableString.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            // Set the color for the rest of the text to white
            val whiteSpan = ForegroundColorSpan(Color.WHITE)
            spannableString.setSpan(whiteSpan, 0, spannableString.length - 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            // Set the SpannableString to the TextView
            loginTextView.text = spannableString
        }
    }

    // Returns a ViewHolder holding the inflated layout view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_onboarding, parent, false)
        return OnboardingViewHolder(view)
    }

    // Initialize all the data needed for the view
    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        val currentItem = onboardingItems[position]

        // Bind data to views in the layout
        holder.itemView.findViewById<ImageView>(R.id.imageView).setImageResource(currentItem.imageResource)
        holder.itemView.findViewById<TextView>(R.id.textView).text = currentItem.text

        // Get dots and button
        val dot1 = holder.itemView.findViewById<ImageView>(R.id.bar1)
        val dot2 = holder.itemView.findViewById<ImageView>(R.id.bar2)
        val dot3 = holder.itemView.findViewById<ImageView>(R.id.bar3)
        val nextButton = holder.itemView.findViewById<Button>(R.id.nextButton)

        // Adjust visibility of dots and button based on the current page
        when (position) {
            0 -> {
                dot1.setBackgroundResource(R.drawable.dot_selected)
                dot2.setBackgroundResource(R.drawable.dot_unselected)
                dot3.setBackgroundResource(R.drawable.dot_unselected)
            }
            1 -> {
                dot1.setBackgroundResource(R.drawable.dot_unselected)
                dot2.setBackgroundResource(R.drawable.dot_selected)
                dot3.setBackgroundResource(R.drawable.dot_unselected)
            }
            2 -> {
                dot1.setBackgroundResource(R.drawable.dot_unselected)
                dot2.setBackgroundResource(R.drawable.dot_unselected)
                dot3.setBackgroundResource(R.drawable.dot_selected)
            }
        }

        // Change button visibility based on current page
        if (position == onboardingItems.size - 1) {
            nextButton.visibility = View.VISIBLE
        } else {
            nextButton.visibility = View.GONE
        }
    }

    override fun getItemCount() = onboardingItems.size
}
