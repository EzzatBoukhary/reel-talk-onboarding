/*
Written By: Ezzat Boukhary
Date: 12/17/2023
 */
package com.example.demo.adapter

import android.widget.ImageButton

interface OnItemClickListener {

    // Takes the imageButton clicked and the position in recyclerView
    fun onItemClick(clickedButton: ImageButton, position: Int)
}
