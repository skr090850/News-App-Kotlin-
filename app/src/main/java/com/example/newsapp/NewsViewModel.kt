package com.example.newsapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.Article
import com.example.newsapp.data.NewsRepository
import com.example.newsapp.data.NewsResponse
import com.example.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    val articles: MutableLiveData<Resource<List<Article>>> = MutableLiveData()

    init {
        fetchTopHeadlines()
    }

    private fun fetchTopHeadlines() {
        articles.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                val response = repository.getTopHeadlines()
                articles.postValue(handleNewsResponse(response))
            } catch (t: Throwable) {
                when(t) {
                    is IOException -> articles.postValue(Resource.Error("Network Failure: ${t.message}"))
                    else -> articles.postValue(Resource.Error("Conversion Error: ${t.message}"))
                }
            }
        }
    }

    private fun handleNewsResponse(response: Response<NewsResponse>): Resource<List<Article>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                val articlesList = resultResponse.response?.results ?: emptyList()
                return Resource.Success(articlesList)
            }
        }
        return Resource.Error(response.message())
    }
}

