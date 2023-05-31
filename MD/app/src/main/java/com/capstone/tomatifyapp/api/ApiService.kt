package com.capstone.tomatifyapp.api

import com.capstone.tomatifyapp.model.*
import com.capstone.tomatifyapp.model.LoginResponse
//import com.capstone.tomatifyapp.model.ResponseDetailStory
import com.capstone.tomatifyapp.model.ResponseGeneral
//import com.capstone.tomatifyapp.model.ResponseListStory
import com.capstone.tomatifyapp.model.UserModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json", "X-Requested-With: XMLHttpRequest")
    @POST("login")
    fun login(@Body user: UserModel): Call<LoginResponse>

    @POST("register")
    fun register(@Body user: UserModel): Call<ResponseGeneral>

//    @GET("stories")
//    suspend fun getListStory(@Header("Authorization") authorization: String, @Query("page") page: Int, @Query("size") size: Int): ResponseListStory
//
//    @GET("stories")
//    fun getListStory(@Header("Authorization") authorization: String, @Query("location") location: Int): Call<ResponseListStory>
//
//    @GET("stories/{id}")
//    fun getDetailStory(@Header("Authorization") authorization: String, @Path("id") id: String): Call<ResponseDetailStory>
//
//    @Multipart
//    @POST("stories")
//    fun addStory(@Header("Authorization") authorization: String, @Part("description") description: RequestBody, @Part file: MultipartBody.Part): Call<ResponseGeneral>

}