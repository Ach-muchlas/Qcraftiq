package com.am.finalproject.data.remote

import com.google.gson.annotations.SerializedName

data class ModuleResponse(

	@field:SerializedName("data")
	val data: List<DataItemModule>,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItemModule(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("video")
	val video: String? = null,

	@field:SerializedName("time")
	val time: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("courseId")
	val courseId: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null,

	@field:SerializedName("chapter")
	val chapter: Int

)
