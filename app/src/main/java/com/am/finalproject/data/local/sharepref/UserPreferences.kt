package com.am.finalproject.data.local.sharepref

import android.content.Context
import android.content.SharedPreferences
import com.am.finalproject.data.remote.LoginResult

class UserPreferences(context: Context) {

    companion object {
        private const val PREF_NAME = "user_pref"
        private const val KEY_TOKEN = "accessToken"
        private const val KEY_LOGIN = "isLogin"
        private const val KEY_ON_BOARDING = "onBoarding"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUser(user: LoginResult) {
        sharedPreferences.edit().apply {
            putString(KEY_TOKEN, user.accessToken)
            putBoolean(KEY_LOGIN, true)
            apply()
        }
    }

    fun getUser(): LoginResult? {
        val token = sharedPreferences.getString(KEY_TOKEN, null)
        return if (token != null) {
            LoginResult(token)
        } else {
            null
        }
    }

    fun isUserLogin(): Boolean {
        return sharedPreferences.getBoolean(KEY_LOGIN, false)
    }

    fun clearUser() {
        sharedPreferences.edit().clear().apply()
    }

    fun isOnBoardingCompleted(): Boolean {
        return sharedPreferences.getBoolean(KEY_ON_BOARDING, false)
    }

    fun markOnBoardingCompleted() {
        sharedPreferences.edit().putBoolean(KEY_ON_BOARDING, true).apply()
    }
}