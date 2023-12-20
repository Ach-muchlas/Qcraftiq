package com.am.finalproject.ui.detail_payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.am.finalproject.R
import com.am.finalproject.databinding.FragmentDetailPaymentBinding
import com.am.finalproject.ui.auth.AuthViewModel
import org.koin.android.ext.android.inject

class DetailPaymentFragment : Fragment() {
    private lateinit var binding: FragmentDetailPaymentBinding
    private val viewModel: AuthViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_payment, container, false)
    }

    private fun setupEditText (){
        binding
    }

}