package com.capstone.tomatifyapp.ui.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.tomatifyapp.data.AuthRepository
import com.capstone.tomatifyapp.model.LoginResponse
import com.capstone.tomatifyapp.model.ResponseGeneral
import com.capstone.tomatifyapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {

        fun login(email: String, password: String): LiveData<Result<LoginResponse>> =
            authRepository.login(email, password)

        fun register(name: String, email: String, password: String): LiveData<Result<ResponseGeneral>> =
            authRepository.register(name, email, password)

}