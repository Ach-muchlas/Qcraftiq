package com.am.finalproject.data.retrofit

import com.am.finalproject.data.remote.CategoryResponse
import com.am.finalproject.data.remote.CourseResponse
import com.am.finalproject.data.remote.LoginBody
import com.am.finalproject.data.remote.LoginResponse
import com.am.finalproject.data.remote.ModuleResponse
import com.am.finalproject.data.remote.RegisterBody
import com.am.finalproject.data.remote.RegisterBodyWithOTP
import com.am.finalproject.data.remote.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {

    @GET("courses")
    suspend fun getPopularCourse(): CourseResponse

    @GET("categories")
    suspend fun getCategoryCourse(): CategoryResponse

    @POST("auth/login")
    suspend fun login(
        @Body loginBody: LoginBody
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body registerBody: RegisterBody
    ): RegisterResponse

    @POST("auth/register/otp")
    suspend fun sendOTP(
        @Body registerBody: RegisterBodyWithOTP
    ): RegisterResponse

    @FormUrlEncoded
    @PUT("auth/register/resend-otp")
    suspend fun resendOTP(
        @Field("email") email: String
    ): RegisterResponse
    @GET("courses")
    fun getCourse(
    ) : Call<CourseResponse>

    @GET("modules")
    suspend fun getModules(
        @Header("Authorization") bearer : String
    ): ModuleResponse
}