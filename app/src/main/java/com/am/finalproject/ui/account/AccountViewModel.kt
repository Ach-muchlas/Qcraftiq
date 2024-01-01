package com.am.finalproject.ui.account

import android.content.Context
import androidx.lifecycle.ViewModel
import com.am.finalproject.data.local.sharepref.UserPreferences
import com.am.finalproject.data.remote.LoginResult
import com.am.finalproject.data.source.Repository

class AccountViewModel(private val repository: Repository) : ViewModel() {
    private lateinit var sharedpref: UserPreferences

    fun init(context: Context) {
        sharedpref = UserPreferences(context)
    }

    fun saveUser(user: LoginResult) {
        return sharedpref.saveUser(user)
    }

    fun logout() = sharedpref.clearUser()

    fun getUser() = sharedpref.getUser()

    fun isUserLogin(): Boolean {
        return sharedpref.isUserLogin()
    }


    fun changePasswordUser(password: String, newPassword: String, token: String) =
        repository.changePassword(password, newPassword, token)

    fun getCurrentUser(token: String) = repository.getCurrentUser(token)
    fun updateUser(
        token: String,
        name: String,
        email: String,
        phone: String,
        country: String,
        city: String,
        image: String
    ) = repository.updateUser(token, name, email, phone, country, city, image)
}