package com.am.finalproject.data.retrofit

import com.am.finalproject.data.remote.CategoryResponse
import com.am.finalproject.data.remote.CourseResponse
import com.am.finalproject.data.remote.LoginBody
import com.am.finalproject.data.remote.LoginResponse
import com.am.finalproject.data.remote.NotificationResponse
import com.am.finalproject.data.remote.PaymentBody
import com.am.finalproject.data.remote.PaymentResponse
import com.am.finalproject.data.remote.RegisterBody
import com.am.finalproject.data.remote.RegisterBodyWithOTP
import com.am.finalproject.data.remote.RegisterResponse
import com.am.finalproject.data.remote.TrackingClassResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("courses")
    suspend fun getPopularCourse(): CourseResponse

    @GET("course/{courseId}")
    suspend fun getCourseById(
        @Header("Authorization") bearer: String,
        @Path("courseId") courseId: String
    ): CourseResponse

    @GET("categories")
    suspend fun getCategoryCourse(): CategoryResponse

    @POST("auth/login")
    suspend fun login(
        @Body loginBody: LoginBody
    ): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(
        @Body registerBody: RegisterBody
    ): Response<RegisterResponse>

    @POST("auth/register/otp")
    suspend fun sendOTP(
        @Body registerBody: RegisterBodyWithOTP
    ): Response<RegisterResponse>

    @FormUrlEncoded
    @POST("orders")
    suspend fun orders(
        @Header("Authorization") bearer: String,
        @Field("payment") payment: String,
        @Body paymentBody: PaymentBody
    ): Response<PaymentResponse>

    @FormUrlEncoded
    @PUT("auth/register/resend-otp")
    suspend fun resendOTP(
        @Field("email") email: String
    ): Response<RegisterResponse>

    @FormUrlEncoded
    @PUT("auth/reset-password")
    suspend fun resetPassword(
        @Field("email") email: String
    ): Response<RegisterResponse>

    @FormUrlEncoded
    @PUT("users/change-password")
    suspend fun changePasswordUser(
        @Header("Authorization") bearer: String,
        @Field("password") password: String,
        @Field("newPassword") newPassword: String
    ): Response<RegisterResponse>

    @GET("notification/user")
    suspend fun getNotification(@Header("Authorization") bearer: String): Response<NotificationResponse>

    @GET("courseTrackings/user")
    suspend fun getTrackingClass(@Header("Authorization") bearer: String): Response<TrackingClassResponse>


}