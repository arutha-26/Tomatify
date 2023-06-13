package com.capstone.tomatifyapp.ui.predict

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.tomatifyapp.api.ApiConfig
import com.capstone.tomatifyapp.model.ResponseGeneral
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PredictViewModel : ViewModel() {
    private val predictApi = ApiConfig.getPredictApiService()

    fun uploadPhoto(imageFile: File): LiveData<ResponseGeneral?> {
        val responseLiveData = MutableLiveData<ResponseGeneral?>()

        val requestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val photoPart = MultipartBody.Part.createFormData("file", imageFile.name, requestBody)

        val call = predictApi.predict(photoPart)
        call.enqueue(object : Callback<ResponseGeneral> {

            override fun onResponse(call: Call<ResponseGeneral>, response: Response<ResponseGeneral>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        responseLiveData.value = result
                    } else {
                        // Handle null response body
                        responseLiveData.value = ResponseGeneral(
                            message = "Failed to upload photo",
                            error = true
                        )
                    }
                } else {
                    // Handle error response
                    responseLiveData.value = ResponseGeneral(
                        message = "Failed to upload photo",
                        error = true
                    )
                }
            }

            override fun onFailure(call: Call<ResponseGeneral>, t: Throwable) {
                // Handle failure
                responseLiveData.value = ResponseGeneral(
                    message = t.message ?: "Unknown error occurred",
                    error = true
                )
            }
        })

        return responseLiveData
    }

}

