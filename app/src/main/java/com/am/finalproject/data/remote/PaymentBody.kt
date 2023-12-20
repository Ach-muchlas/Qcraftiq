package com.am.finalproject.data.remote


import com.google.gson.annotations.SerializedName

data class PaymentBody(
    @SerializedName("courseId")
    val courseId: Int,
    @SerializedName("payment")
    val payment: Payment
)