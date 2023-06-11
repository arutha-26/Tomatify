package com.capstone.tomatifyapp.model

import com.google.gson.annotations.SerializedName


data class NewsResponse(
    @SerializedName("data") val newsItems: List<NewsItem>
)
