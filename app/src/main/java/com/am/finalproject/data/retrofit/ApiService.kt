package com.am.finalproject.data.retrofit

import com.am.finalproject.data.remote.CategoryResponse
import com.am.finalproject.data.remote.CourseResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("courses")
    suspend fun getPopularCourse() : CourseResponse

    @GET("categories")
    suspend fun getCategoryCourse() : CategoryResponse
}