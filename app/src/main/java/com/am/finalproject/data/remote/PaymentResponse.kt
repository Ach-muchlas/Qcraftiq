package com.am.finalproject.data.remote

import com.google.gson.annotations.SerializedName

data class PaymentResponse(
	@field:SerializedName("data")
	val data: Any? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
