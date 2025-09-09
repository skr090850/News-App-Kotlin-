package com.example.newsapp.api

import com.example.newsapp.data.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("search?show-fields=thumbnail,trailText")
    suspend fun getTopHeadlines(
        @Query("api-key") apiKey: String
    ): Response<NewsResponse>
}

