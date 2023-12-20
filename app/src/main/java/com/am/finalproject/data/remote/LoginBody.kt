package com.am.finalproject.data.remote

import com.google.gson.annotations.SerializedName

data class LoginBody(
    @field:SerializedName("emailOrPhone") val emailOrPhone : String,
    @field:SerializedName("password") val password : String,
)