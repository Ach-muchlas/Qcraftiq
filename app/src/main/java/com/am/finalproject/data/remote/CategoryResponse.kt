package com.am.finalproject.data.remote

import com.google.gson.annotations.SerializedName

data class CategoryResponse(

	@field:SerializedName("data")
	val data: List<DataItemCategory?>? = null,

	@field:SerializedName("message")
	val message: Any? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItemCategory(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
