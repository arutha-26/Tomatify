package com.capstone.tomatifyapp.model


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("error")
    var error: Boolean,
    @SerializedName("loginResult")
    var loginResult: LoginResult? = null,
    @SerializedName("message")
    var message: String
)