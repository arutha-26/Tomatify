package com.capstone.tomatifyapp.model

import com.google.gson.annotations.SerializedName

data class ResponsePredict(
    @SerializedName("Predict")
    val listPredict: List<Predict>?,

    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String
)




