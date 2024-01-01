package com.am.finalproject.ui.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.am.finalproject.databinding.FragmentIsLoginRequiredBottomSheetBinding
import com.am.finalproject.ui.auth.AuthViewModel
import com.am.finalproject.ui.auth.login.LoginActivity
import com.am.finalproject.utils.Navigate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

class IsLoginRequiredBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentIsLoginRequiredBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIsLoginRequiredBottomSheetBinding.inflate(inflater, container, false)
        navigation()
        return binding.root
    }

    private fun navigation() {
        binding.imageViewCloseButton.setOnClickListener {
            dismiss()
        }

        binding.buttonLogin.setOnClickListener {
            Navigate.intentActivityUseFinish(requireContext(), LoginActivity::class.java)
        }
    }

    companion object {
        fun show(fragmentManager: FragmentManager) {
            val bottomSheetFilter = IsLoginRequiredBottomSheet()
            bottomSheetFilter.show(fragmentManager, bottomSheetFilter.tag)
        }
    }

}