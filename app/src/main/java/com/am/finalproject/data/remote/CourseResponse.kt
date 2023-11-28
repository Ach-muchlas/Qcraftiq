package com.am.finalproject.data.remote

import com.google.gson.annotations.SerializedName

data class CourseResponse(

	@field:SerializedName("data")
	val data: List<DataItemCourse?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItemCourse(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("level")
	val level: String? = null,

	@field:SerializedName("authorBy")
	val authorBy: String? = null,

	@field:SerializedName("rating")
	val rating: Any? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("subtitle")
	val subtitle: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("category")
	val category: DataItemCategory? = null,

)
