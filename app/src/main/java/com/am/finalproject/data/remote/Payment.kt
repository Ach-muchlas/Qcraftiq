package com.am.finalproject.data.remote


import com.google.gson.annotations.SerializedName

data class Payment(
    @field:SerializedName("amount")
    val amount: Int,
    @field:SerializedName("cardName")
    val cardName: String,
    @field:SerializedName("cardNumber")
    val cardNumber: String,
    @field:SerializedName("cvv")
    val cvv: String,
    @field:SerializedName("expiryDate")
    val expiryDate: String
)