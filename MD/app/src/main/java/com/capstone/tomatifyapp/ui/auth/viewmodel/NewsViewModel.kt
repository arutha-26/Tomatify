package com.capstone.tomatifyapp.ui.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.tomatifyapp.data.NewsRepository
import com.capstone.tomatifyapp.model.NewsItem
import com.capstone.tomatifyapp.model.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {
    private val localNewsLiveData: MutableLiveData<List<NewsItem>> = MutableLiveData()
    private val internationalNewsLiveData: MutableLiveData<List<NewsItem>> = MutableLiveData()
    private val newsRepository: NewsRepository = NewsRepository()

    fun getLocalNews(): LiveData<List<NewsItem>> {
        newsRepository.getLocalNews(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    localNewsLiveData.postValue(response.body()?.newsItems)
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                // Handle failure
            }
        })

        return localNewsLiveData
    }

    fun getInternationalNews(): LiveData<List<NewsItem>> {
        newsRepository.getInternationalNews(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    internationalNewsLiveData.postValue(response.body()?.newsItems)
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                // Handle failure
            }
        })

        return internationalNewsLiveData
    }
}
