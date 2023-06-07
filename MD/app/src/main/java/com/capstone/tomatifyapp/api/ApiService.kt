package com.capstone.tomatifyapp.api

import com.capstone.tomatifyapp.model.*
import com.capstone.tomatifyapp.model.LoginResponse
//import com.capstone.tomatifyapp.model.ResponseDetailStory
import com.capstone.tomatifyapp.model.ResponseGeneral
//import com.capstone.tomatifyapp.model.ResponseListStory
import com.capstone.tomatifyapp.model.UserModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json", "X-Requested-With: XMLHttpRequest")
    @POST("login")
    fun login(@Body user: UserModel): Call<LoginResponse>

    @POST("register")
    fun register(@Body user: UserModel): Call<ResponseGeneral>

    @GET("newsInter")
    suspend fun getInternational() : List<NewsModel>

    @GET("newsLokal")
    suspend fun getNational(): List<NewsModel>
}