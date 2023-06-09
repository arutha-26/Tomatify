package com.capstone.tomatifyapp.data


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.tomatifyapp.api.ApiService
import com.capstone.tomatifyapp.model.LoginResponse
import com.capstone.tomatifyapp.model.ResponseGeneral
import com.capstone.tomatifyapp.model.UserModel
import com.capstone.tomatifyapp.utils.Result
import com.capstone.tomatifyapp.utils.convertErrorData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: ApiService) {

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> {
        val data: MutableLiveData<Result<LoginResponse>> = MutableLiveData()
        data.postValue(Result.Loading())

        val userModel = UserModel(email = email, password = password)
        try {
            apiService.login(userModel).enqueue(object : Callback<LoginResponse>{
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        data.postValue(Result.Success(response.body() as LoginResponse))
                    }
                    else {
                        val errorData = response.errorBody()?.string()?.let { convertErrorData(it) }
                        data.postValue(Result.Error(errorData?.message, response.code(), null))
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    data.postValue(Result.Error(t.message.toString(), null, null))
                }

            })
        }catch (e: Exception) {
            e.printStackTrace()
            data.postValue(Result.Error("error convert data", null, null))
        }


        return data

    }

    fun register(name: String, email: String, password:String): LiveData<Result<ResponseGeneral>> {

        val data: MutableLiveData<Result<ResponseGeneral>> = MutableLiveData()
        data.postValue(Result.Loading())
        val userData = UserModel(name = name, email = email, password = password)

        try {
            apiService.register(userData).enqueue(object: Callback<ResponseGeneral>{
                override fun onResponse(
                    call: Call<ResponseGeneral>,
                    response: Response<ResponseGeneral>
                ) {
                    if (response.isSuccessful) {
                        data.postValue(Result.Success(response.body() as ResponseGeneral))
                    }
                    else {
                        val errorData = response.errorBody()?.let { convertErrorData(it.string()) }
                        data.postValue(Result.Error(errorData?.message, response.code(), null))
                    }
                }

                override fun onFailure(call: Call<ResponseGeneral>, t: Throwable) {
                    data.postValue(Result.Error(t.message.toString(), null, null))
                }

            })
        }catch (e: Exception) {
            e.printStackTrace()
            data.postValue(Result.Error("error convert data", null, null))
        }



        return data
    }

}