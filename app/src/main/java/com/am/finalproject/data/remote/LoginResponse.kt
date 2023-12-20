package com.am.finalproject.data.remote

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: LoginResult,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String? = null
)

data class LoginResult(
	@field:SerializedName("accessToken")
	val accessToken: String? = null
)


