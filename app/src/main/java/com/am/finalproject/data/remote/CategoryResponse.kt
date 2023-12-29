package com.am.finalproject.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CategoryResponse(

	@field:SerializedName("data")
	val data: List<DataItemCategory>,

	@field:SerializedName("message")
	val message: Any? = null,

	@field:SerializedName("status")
	val status: String? = null
)

@Parcelize
data class DataItemCategory(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String
) : Parcelable
