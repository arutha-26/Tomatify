package com.capstone.tomatifyapp.ui.predict

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.tomatifyapp.api.ApiConfig
import com.capstone.tomatifyapp.model.Predict
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
    private val _responseLiveData = MutableLiveData<Predict?>()
    val responseLiveData : LiveData<Predict?> = _responseLiveData

    fun uploadImg(imageFile: File){
        val requestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val photoPart = MultipartBody.Part.createFormData("file", imageFile.name, requestBody)

        val client = predictApi.predict(photoPart)
        client.enqueue(object  : Callback<Predict>{
            override fun onResponse(call: Call<Predict>, response: Response<Predict>) {
                if (response.isSuccessful){
                    val result = response.body()
                    if (result != null) {
                        _responseLiveData.value = result
                    }
                }
            }

            override fun onFailure(call: Call<Predict>, t: Throwable) {
//                _responseLiveData.value = t.message.toString()
                Log.e("predict view model", "on failure:${t.message.toString()}")
            }

        })
    }

}

