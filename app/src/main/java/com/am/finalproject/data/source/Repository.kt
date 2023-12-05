package com.am.finalproject.data.source

import androidx.lifecycle.liveData
import com.am.finalproject.data.remote.LoginBody
import com.am.finalproject.data.remote.RegisterBody
import com.am.finalproject.data.remote.RegisterBodyWithOTP
import com.am.finalproject.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers

class Repository(
    private val apiService: ApiService,
) {
    fun loginUser(emailOrPhone: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val user = LoginBody(emailOrPhone, password)
            emit(Resource.success(apiService.login(user)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun registerUser(name: String, email: String, phone: String, password: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val user = RegisterBody(name, email, phone, password)
                emit(Resource.success(apiService.register(user)))
            } catch (exception: Exception) {
                emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
            }
        }


    fun sendOTP(name: String, email: String, phone: String, password: String, otp: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                val user = RegisterBodyWithOTP(name, email, phone, password, otp)
                emit(Resource.success(apiService.sendOTP(user)))
            } catch (exception: Exception) {
                emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
            }
        }

    fun resendOTP(email: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(apiService.resendOTP(email)))
        } catch (exception: Exception) {
            emit(Resource.error(null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getPopularCourse() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(
                    data = apiService.getPopularCourse(),
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, exception.message ?: "Error Occurred!!"))
        }
    }

    fun getCategory() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(
                Resource.success(
                    data = apiService.getCategoryCourse(),
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, exception.message ?: "Error Occurred!!"))
        }
    }
}