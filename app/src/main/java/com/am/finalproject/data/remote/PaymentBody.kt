package com.am.finalproject.data.remote

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class PaymentBody(

	@field:SerializedName("payment")
	val payment: Payment? = null,

	@field:SerializedName("courseId")
	val courseId: String? = null
)

data class Payment(

	@field:SerializedName("expiryDate")
	val expiryDate: String? = null,

	@field:SerializedName("cvv")
	val cvv: Int? = null,

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("cardName")
	val cardName: String? = null,

	@field:SerializedName("cardNumber")
	val cardNumber: String? = null
)
