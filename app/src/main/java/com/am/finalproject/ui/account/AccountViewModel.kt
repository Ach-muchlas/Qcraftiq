package com.am.finalproject.ui.account

import android.content.Context
import androidx.lifecycle.ViewModel
import com.am.finalproject.data.local.sharepref.UserPreferences
import com.am.finalproject.data.source.Repository

class AccountViewModel(private val repository: Repository) : ViewModel() {
    private lateinit var sharedpref: UserPreferences

    fun init(context: Context) {
        sharedpref = UserPreferences(context)
    }

    fun logout() {
        sharedpref.clearUser()
    }

    fun changePasswordUser(password: String, newPassword: String, token : String) =
        repository.changePassword(password, newPassword, token)
}