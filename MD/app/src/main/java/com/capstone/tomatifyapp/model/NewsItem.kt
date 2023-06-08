package com.capstone.tomatifyapp.model

import com.google.gson.annotations.SerializedName

data class NewsItem(

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("excerpt")
    val excerpt: String,

    @field:SerializedName("category")
    val category: String,

    )