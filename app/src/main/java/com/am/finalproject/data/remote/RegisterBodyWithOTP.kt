package com.am.finalproject.data.remote

import com.google.gson.annotations.SerializedName

data class RegisterBodyWithOTP(
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("phone")
    val phone: String,
    @field:SerializedName("password")
    val password: String,
    @field:SerializedName("otp")
    val otp: String,
)
