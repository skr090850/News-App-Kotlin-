package com.example.newsapp.data

import com.example.newsapp.api.RetrofitInstance

class NewsRepository {
    private val apiKey = "a3ca16b1-492d-416e-9d9e-39301dcae367"

    suspend fun getTopHeadlines() = RetrofitInstance.api.getTopHeadlines(apiKey)
}

