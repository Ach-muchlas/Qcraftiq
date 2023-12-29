package com.am.finalproject.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CourseResponse(

	@field:SerializedName("data")
	val data: List<DataItemCourse>,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

@Parcelize
data class DataItemCourse(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("level")
	val level: String,

	@field:SerializedName("authorBy")
	val authorBy: String,

	@field:SerializedName("rating")
	val rating: Double,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("subtitle")
	val subtitle: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("category")
	val category: DataItemCategory,

	@field:SerializedName("module")
	val module: List<DataItemModule>? = null,

	@field:SerializedName("telegram")
	val telegram: String,
) : Parcelable


