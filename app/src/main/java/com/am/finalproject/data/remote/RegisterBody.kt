package com.am.finalproject.data.remote

import com.google.gson.annotations.SerializedName

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
