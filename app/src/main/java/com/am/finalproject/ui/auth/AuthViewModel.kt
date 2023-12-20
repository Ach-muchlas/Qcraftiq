package com.am.finalproject.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import com.am.finalproject.data.local.sharepref.UserPreferences
import com.am.finalproject.data.remote.LoginResult
import com.am.finalproject.data.source.Repository

class AuthViewModel(private val repository: Repository) : ViewModel() {
    private lateinit var sharedpref: UserPreferences

    fun init(context: Context) {
        sharedpref = UserPreferences(context)
    }

    fun loginUser(emailOrPhone: String, password: String) = repository.loginUser(emailOrPhone, password)

    fun register(name: String, email: String, phone: String, password: String) =
        repository.registerUser(name, email, phone, password)

    fun sendOTP(name: String, email: String, phone: String, password: String, otp: String) =
        repository.sendOTP(name, email, phone, password, otp)

    fun resendOTP(email: String) = repository.resendOTP(email)

    fun resetPassword(email: String) = repository.resetPassword(email)
    fun saveUser(user: LoginResult) {
        return sharedpref.saveUser(user)
    }

    fun isUserLogin(): Boolean {
        return sharedpref.isUserLogin()
    }

    fun getUser(): LoginResult? {
        return sharedpref.getUser()
    }

    fun isOnBoardingCompleted(): Boolean{
        return sharedpref.isOnBoardingCompleted()
    }

    fun markOnBoardingCompleted() = sharedpref.markOnBoardingCompleted()
}