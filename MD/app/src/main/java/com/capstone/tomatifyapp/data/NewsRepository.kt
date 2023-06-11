package com.capstone.tomatifyapp.data

import com.capstone.tomatifyapp.api.ApiConfig.provideApiService
import com.capstone.tomatifyapp.api.ApiConfig.provideOkHttpClient
import com.capstone.tomatifyapp.api.ApiConfig.provideRetrofit
import com.capstone.tomatifyapp.api.ApiService
import com.capstone.tomatifyapp.model.NewsResponse
import retrofit2.Call
import retrofit2.Callback

class NewsRepository {

   val apiService: ApiService by lazy { provideApiService(provideRetrofit(provideOkHttpClient())) }

    fun getLocalNews(callback: Callback<NewsResponse>) {
        val call: Call<NewsResponse> = apiService.getLocalNews()
        call.enqueue(callback)
    }

    fun getInternationalNews(callback: Callback<NewsResponse>) {
        val call: Call<NewsResponse> = apiService.getInternationalNews()
        call.enqueue(callback)
    }
}

