package com.am.finalproject.data.remote

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("data")
    val data: Any? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class RegisterBody(
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("phone")
    val phone: String,
    @field:SerializedName("password")
    val password: String,
)

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

