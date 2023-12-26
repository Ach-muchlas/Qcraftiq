package com.am.finalproject.ui.bottom_sheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.am.finalproject.R
import com.am.finalproject.databinding.FragmentFilterCourseBottomSheetBinding
import com.am.finalproject.databinding.FragmentOrderBottomSheetBinding
import com.am.finalproject.ui.detail_payment.DetailPaymentActivity
import com.am.finalproject.ui.detail_payment.DetailPaymentFragment
import com.am.finalproject.utils.Destination
import com.am.finalproject.utils.Navigate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class OrdersBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding : FragmentOrderBottomSheetBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBottomSheetBinding.inflate(inflater, container, false)


        navigation()
        return binding.root
    }

    private fun navigation(){
        val bundle = arguments
        val id = bundle?.getString(KEY_ID)

        binding.textViewDataCard.text = id

        binding.button.setOnClickListener {
            val parsingData = Bundle().apply {
                putString(KEY_ID, id)
            }
            val intent = Intent(requireContext(), DetailPaymentActivity::class.java).apply {
                putExtras(parsingData)
            }

            startActivity(intent)
        }
    }

    companion object {
        const val KEY_ID = "key_id"
        fun show(fragmentManager: FragmentManager, id : String) {
            val bottomSheetFilter = OrdersBottomSheetFragment()
            val bundle = Bundle()
            bundle.putString(KEY_ID, id)
            bottomSheetFilter.arguments = bundle
            bottomSheetFilter.show(fragmentManager, bottomSheetFilter.tag)
        }
    }

}