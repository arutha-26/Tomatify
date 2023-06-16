package com.capstone.tomatifyapp.model


import com.google.gson.annotations.SerializedName

data class ResponseGeneral(
    @SerializedName("error")
    var error: Boolean,
    @SerializedName("message")
    var message: String
)