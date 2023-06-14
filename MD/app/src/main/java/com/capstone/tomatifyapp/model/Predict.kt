package com.capstone.tomatifyapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Predict(
    @SerializedName("file")
    val photoUrl: String? = null,

    @SerializedName("confidence")
    val rate: Double? = null,

    @SerializedName("class")
    val name: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("prevention")
    val prevention: String? = null
):Parcelable