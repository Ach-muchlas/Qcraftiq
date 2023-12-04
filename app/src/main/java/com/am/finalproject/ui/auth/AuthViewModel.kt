package com.am.finalproject.ui.auth

import androidx.lifecycle.ViewModel
import com.am.finalproject.data.source.Repository

class AuthViewModel(private val repository: Repository) : ViewModel() {
    fun login(emailOrPhone: String, password: String) = repository.loginUser(emailOrPhone, password)

    fun register(name: String, email: String, phone: String, password: String) =
        repository.registerUser(name, email, phone, password)

    fun sendOTP(name: String, email: String, phone: String, password: String, otp: String) =
        repository.sendOTP(name, email, phone, password, otp)

    fun resendOTP(email : String) = repository.resendOTP(email)
}