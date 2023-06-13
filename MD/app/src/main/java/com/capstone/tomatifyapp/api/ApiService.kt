package com.capstone.tomatifyapp.api


import com.capstone.tomatifyapp.model.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json", "X-Requested-With: XMLHttpRequest")
    @POST("login")
    fun login(@Body user: UserModel): Call<LoginResponse>

    @POST("register")
    fun register(@Body user: UserModel): Call<ResponseGeneral>

    @GET("newsInter")
    fun getInternationalNews(): Call<NewsResponse>

    @GET("newsLokal")
    fun getLocalNews(): Call<NewsResponse>

    @Multipart
    @POST("predict")
    fun predict(@Part file: MultipartBody.Part): Call<ResponseGeneral>
}
