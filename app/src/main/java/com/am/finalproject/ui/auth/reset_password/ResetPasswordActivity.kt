package com.am.finalproject.ui.auth.reset_password

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.am.finalproject.R
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.ActivityResetPasswordBinding
import com.am.finalproject.ui.auth.AuthViewModel
import com.am.finalproject.utils.DisplayLayout
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.android.ext.android.inject

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private val viewModel: AuthViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        DisplayLayout.hideAppBar(this)
        setContentView(binding.root)
        setupEditText()
        navigation()
    }

    private fun setupEditText() {
        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (text.toString().isEmpty()) {
                    binding.edlEmail.error = getString(R.string.warning_text_field_empty)
                } else if (!DisplayLayout.isValidEmail(text) && !DisplayLayout.isValidPhoneNumber(
                        text.toString()
                    )
                ) {
                    binding.edlEmail.error = getString(R.string.warning_text_email_invalid)
                } else {
                    binding.edlEmail.error = null
                    DisplayLayout.buttonEnabled(
                        binding.buttonResetPassword,
                        binding.edtEmail,
                        this@ResetPasswordActivity
                    )
                }

            }

            override fun afterTextChanged(text: Editable) {
                if (DisplayLayout.isValidEmail(text)) {
                    binding.edlEmail.endIconDrawable =
                        ContextCompat.getDrawable(
                            this@ResetPasswordActivity,
                            R.drawable.check_circle
                        )
                } else {
                    binding.edlEmail.endIconDrawable = null
                }
            }
        })
    }

    private fun navigation() {
        binding.apply {
            val textEmail = edtEmail.text
            buttonResetPassword.setOnClickListener {
                viewModel.resetPassword(email = textEmail.toString())
                    .observe(this@ResetPasswordActivity) { result ->
                        when (result.status) {
                            Status.LOADING -> {}
                            Status.SUCCESS -> {
                                StyleableToast.makeText(
                                    this@ResetPasswordActivity,
                                    result.data?.message,
                                    Toast.LENGTH_SHORT,
                                    R.style.MyToast_IsGreen
                                ).show()
                                textEmail?.clear()
                            }

                            Status.ERROR -> {
                                StyleableToast.makeText(
                                    this@ResetPasswordActivity,
                                    result.message,
                                    Toast.LENGTH_SHORT,
                                    R.style.MyToast_IsRed
                                ).show()
                            }
                        }

                    }
            }
        }
    }

}