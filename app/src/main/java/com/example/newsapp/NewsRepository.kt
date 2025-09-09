package com.example.newsapp.data

import com.example.newsapp.api.RetrofitInstance

class NewsRepository {
    suspend fun getTopHeadlines() = RetrofitInstance.api.getTopHeadlines()
}
