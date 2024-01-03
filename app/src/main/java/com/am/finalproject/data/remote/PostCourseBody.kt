package com.am.finalproject.data.remote

import com.google.gson.annotations.SerializedName

data class PostCourseBody(
    @field:SerializedName("status") val status : String,
    @field:SerializedName("userId") val userId : String,
    @field:SerializedName("courseId") val courseId : String,
)