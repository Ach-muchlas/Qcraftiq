package com.am.finalproject.data.remote

import com.google.gson.annotations.SerializedName

data class TrackingClassResponse(

	@field:SerializedName("data")
	val data: List<DataItemTrackingClass>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class User(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("resetTokenExpiredAt")
	val resetTokenExpiredAt: Any? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("resetToken")
	val resetToken: Any? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class DataItemTrackingClass(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("course")
	val course: DataItemCourse? = null,

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
