package com.am.finalproject.data.remote

import com.google.gson.annotations.SerializedName

data class ChangePasswordBody(
    @field:SerializedName("password")
    val password: String,
    @field:SerializedName("newPassword")
    val newPassword: String
)