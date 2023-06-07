package com.capstone.tomatifyapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.tomatifyapp.api.ApiConfig
import com.capstone.tomatifyapp.model.NewsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsViewModel : ViewModel(){


private val _national = MutableLiveData<List<NewsModel>>()
    val national: LiveData<List<NewsModel>> = _national

    private val _international = MutableLiveData<List<NewsModel>?>()
    val international: LiveData<List<NewsModel>> = _international

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun getNational() {
        withContext(Dispatchers.IO) {
            _isLoading.postValue(true)
            val apiService = ApiConfig.getApiService(ApiConfig.NEWS_URL)
            try {
                val response = apiService.getNational()
                if (response.isSuccessful) {
                    _isLoading.postValue(false)
                    val data = response.body()
                    _national.postValue(data)
                } else {
                    Log.e(this::class.java.simpleName, "Request failed with code: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(this::class.java.simpleName, "On Failure ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    suspend fun getInternational() {
        withContext(Dispatchers.IO) {
            _isLoading.postValue(true)
            val apiService = ApiConfig.getApiService(ApiConfig.NEWS_URL)
            try {
                val response = apiService.getInternational()
                if (response.isSuccessful) {
                    _isLoading.postValue(false)
                    val data = response.body()
                    _international.postValue(data)
                } else {
                    Log.e(this::class.java.simpleName, "Request failed with code: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(this::class.java.simpleName, "On Failure ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }



}