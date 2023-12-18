/*
Written By: Ezzat Boukhary
Date: 12/15/2023
 */
package com.example.demo

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    // Create a single instance of Retrofit
    companion object {
        private const val BASE_URL = "https://moviesdatabase.p.rapidapi.com"

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }
    }
}