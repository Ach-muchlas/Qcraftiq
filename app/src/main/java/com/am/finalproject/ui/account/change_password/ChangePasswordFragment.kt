package com.am.finalproject.ui.account.change_password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.am.finalproject.R
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.FragmentChangePasswordBinding
import com.am.finalproject.ui.account.AccountViewModel
import com.am.finalproject.ui.auth.AuthViewModel
import com.am.finalproject.utils.DisplayLayout
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.android.ext.android.inject

class ChangePasswordFragment : Fragment() {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AccountViewModel by inject()
    private val authViewModel : AuthViewModel by inject()

    private val editTextList by lazy {
        listOf(
            binding.edtOldPassword,
            binding.edtNewPassword,
            binding.edtRepeatPassword,
        )
    }

    private val editLayout by lazy {
        listOf(
            binding.edlOldPassword,
            binding.edlNewPassword,
            binding.edlRepeatNewPassword,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        setupEditText()
        changePassword()
        navigation()
        return binding.root
    }

    private fun setupEditText() {
        for (i in editTextList.indices) {
            editTextList[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                    if (text.toString().isEmpty()) {
                        editLayout[i].error =
                            getString(R.string.warning_text_field_empty)
                    } else if (!DisplayLayout.containsUpperCase(text)) {
                        editLayout[i].error =
                            getString(R.string.warning_password_uppercase)
                    } else if (!DisplayLayout.containsSpecialCharacter(text)) {
                        editLayout[i].error =
                            getString(R.string.warning_password_uppercase)
                    } else {
                        editLayout[i].error = null
                    }
                    binding.buttonChangePassword.isEnabled =
                        editTextList[1].text.toString() == editTextList[2].text.toString()
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
        }

    }

    private fun changePassword() {
        val oldPassword = binding.edtOldPassword.text
        val newPassword = binding.edtNewPassword.text
        authViewModel.init(requireContext())
        val token = authViewModel.getUser()?.accessToken.toString()
        binding.buttonChangePassword.setOnClickListener {
            viewModel.changePasswordUser(oldPassword.toString(), newPassword.toString(), token)
                .observe(viewLifecycleOwner) { resources ->
                    when (resources.status) {
                        Status.LOADING -> {
                            StyleableToast.makeText(
                                requireContext(),
                                "Load",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                        Status.SUCCESS -> {
                            StyleableToast.makeText(
                                requireContext(),
                                resources.data?.message,
                                Toast.LENGTH_SHORT,
                                R.style.MyToast_IsGreen
                            ).show()
                        }

                        Status.ERROR -> {
                            Log.e("SIMPLE", "Error : ${resources.message}")
                            StyleableToast.makeText(
                                requireContext(),
                                resources.message,
                                Toast.LENGTH_SHORT,
                                R.style.MyToast_IsRed
                            ).show()
                        }
                    }

                }
        }
    }

    private fun navigation() {
        binding.apply {
            imageViewButtonBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }


}