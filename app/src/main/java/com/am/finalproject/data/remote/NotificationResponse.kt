package com.am.finalproject.data.remote

import com.google.gson.annotations.SerializedName

data class NotificationResponse(

	@field:SerializedName("data")
	val data: List<DataItemNotification?>? = null,

	@field:SerializedName("message")
	val message: Any? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItemNotification(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("subtitle")
	val subtitle: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
