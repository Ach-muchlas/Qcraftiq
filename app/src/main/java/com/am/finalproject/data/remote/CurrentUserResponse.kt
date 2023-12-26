package com.am.finalproject.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CurrentUserResponse(

	@field:SerializedName("data") val data: DataUser? = null,

	@field:SerializedName("message") val message: Any? = null,

	@field:SerializedName("status") val status: String? = null
)

@Parcelize
data class DataUser(

	@field:SerializedName("image") val image: String? = null,

	@field:SerializedName("country") val country: String? = null,

	@field:SerializedName("phone") val phone: String? = null,

	@field:SerializedName("city") val city: String? = null,

	@field:SerializedName("name") val name: String? = null,

	@field:SerializedName("id") val id: String? = null,

	@field:SerializedName("email") val email: String? = null
) : Parcelable
