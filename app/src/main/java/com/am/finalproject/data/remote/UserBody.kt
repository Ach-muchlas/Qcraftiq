package com.am.finalproject.data.remote

import com.google.gson.annotations.SerializedName

data class UserBody(
    @field:SerializedName("name") val name : String,
    @field:SerializedName("email") val email : String,
    @field:SerializedName("phone") val phone : String,
    @field:SerializedName("country") val country : String,
    @field:SerializedName("city") val city : String,
    @field:SerializedName("image") val image : String,
)
