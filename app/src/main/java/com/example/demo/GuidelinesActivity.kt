/*
Written By: Ezzat Boukhary
Date: 12/17/2023
 */
package com.example.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class GuidelinesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guidelines)

        val agreeButton = findViewById<Button>(R.id.agree_button_guidelines)

        // agree button -> Go to next activity
        agreeButton.setOnClickListener {

            // no next activity yet
            // startActivity(Intent(applicationContext, NextActivity::class.java))
            finish()
        }

    }
}