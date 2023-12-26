package com.am.finalproject.ui.detail_payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.am.finalproject.R
import com.am.finalproject.databinding.ActivityDetailPaymentBinding
import com.am.finalproject.databinding.ActivityDetailsBinding
import com.am.finalproject.ui.bottom_sheet.OrdersBottomSheetFragment
import com.am.finalproject.ui.details.DetailsActivity

class DetailPaymentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receiveBundle = intent.extras
        val id = receiveBundle?.getString(OrdersBottomSheetFragment.KEY_ID)
        binding.textView.text = id
    }
}