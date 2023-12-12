package com.am.finalproject.data.retrofit

import com.am.finalproject.data.remote.CategoryResponse
import com.am.finalproject.data.remote.ChangePasswordBody
import com.am.finalproject.data.remote.ChangePasswordResponse
import com.am.finalproject.data.remote.CourseResponse
import com.am.finalproject.data.remote.LoginBody
import com.am.finalproject.data.remote.LoginResponse
import com.am.finalproject.data.remote.RegisterBody
import com.am.finalproject.data.remote.RegisterBodyWithOTP
import com.am.finalproject.data.remote.RegisterResponse
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

    @PUT("auth/reset-password")
    fun resetPassword(
        @Field("email") email: String
    ): RegisterResponse

    @FormUrlEncoded
    @PUT("users/change-password")
    suspend fun changePasswordUser(
        @Header("Authorization") bearer : String,
        @Field("password") password : String,
        @Field("newPassword") newPassword : String
    ): ChangePasswordResponse
}