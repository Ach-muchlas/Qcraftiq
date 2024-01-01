package com.am.finalproject.data.remote

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: LoginResult? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class LoginResult(
	@field:SerializedName("accessToken")
	val accessToken: String? = null
)

data class LoginBody(
	@field:SerializedName("emailOrPhone") val emailOrPhone : String,
	@field:SerializedName("password") val password : String,
)
