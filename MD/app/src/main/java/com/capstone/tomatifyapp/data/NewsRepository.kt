package com.capstone.tomatifyapp.data

import com.capstone.tomatifyapp.api.ApiConfig
import com.capstone.tomatifyapp.model.NewsResponse
import retrofit2.Call
import retrofit2.Callback

class NewsRepository {

    private val apiService = ApiConfig.getApiService()


    fun getLocalNews(callback: Callback<NewsResponse>) {
        val call: Call<NewsResponse> = apiService.getLocalNews()
        call.enqueue(callback)
    }

    fun getInternationalNews(callback: Callback<NewsResponse>) {
        val call: Call<NewsResponse> = apiService.getInternationalNews()
        call.enqueue(callback)
    }
}

