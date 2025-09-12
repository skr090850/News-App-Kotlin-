package com.example.newsapp.data

data class Article(
    val webTitle: String?,
    val webUrl: String?,
    val fields: ArticleFields?
)

data class ArticleFields(
    val thumbnail: String?,
    val trailText: String?
)

