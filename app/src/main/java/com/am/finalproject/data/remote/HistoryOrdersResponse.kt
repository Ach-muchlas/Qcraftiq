package com.am.finalproject.data.remote

import com.google.gson.annotations.SerializedName

data class HistoryOrdersResponse(

	@field:SerializedName("data")
	val data: List<DataItemHistory?>? = null,

	@field:SerializedName("message")
	val message: Any? = null,

	@field:SerializedName("status")
	val status: String? = null
)


data class DataItemHistory(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("paymentId")
	val paymentId: String? = null,

	@field:SerializedName("course")
	val course: DataItemCourse? = null,

	@field:SerializedName("payment")
	val payment: Payment? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("courseId")
	val courseId: String? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
