package com.example.newsapp

import android.app.Application
import com.example.newsapp.data.NewsRepository

class NewsApplication : Application() {
    val newsRepository by lazy { NewsRepository() }
}

