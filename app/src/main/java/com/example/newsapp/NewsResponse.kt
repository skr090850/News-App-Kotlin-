package com.example.newsapp.data

data class NewsResponse(
    val response: ResponseData?
)

data class ResponseData(
    val results: List<Article>?
)

