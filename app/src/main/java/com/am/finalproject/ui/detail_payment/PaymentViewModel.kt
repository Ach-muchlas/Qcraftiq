package com.am.finalproject.ui.detail_payment

import android.content.Context
import androidx.lifecycle.ViewModel
import com.am.finalproject.data.local.sharepref.UserPreferences
import com.am.finalproject.data.source.Repository
import java.time.LocalDateTime

class PaymentViewModel(private val repository: Repository) : ViewModel() {
    private lateinit var sharedpref: UserPreferences

    fun init(context: Context) {
        sharedpref = UserPreferences(context)
    }

    fun getUser() = sharedpref.getUser()

    fun postOrderCourse(
        token: String,
        expiredDate: String,
        cvv: Int,
        amount: Int,
        cardName: String,
        cardNumber: String,
        courseId: String
    ) = repository.paymentOrders(token, expiredDate, cvv, amount, cardName, cardNumber, courseId)


    fun getHistoryOrderCourse(token: String) = repository.getHistoryOrders(token)
}