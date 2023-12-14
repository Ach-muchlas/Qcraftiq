package com.am.finalproject.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.am.finalproject.R
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.ActivityRegisterBinding
import com.am.finalproject.ui.auth.AuthViewModel
import com.am.finalproject.ui.auth.otp.OtpActivity
import com.am.finalproject.utils.DisplayLayout
import org.koin.android.ext.android.inject

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        navigation()
        setupEditText()
    }

    private fun setupEditText() {
        /*email*/
        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (text.toString().isEmpty()) {
                    binding.edlEmail.error = getString(R.string.warning_text_field_empty)
                } else if (!DisplayLayout.isValidEmail(text)) {
                    binding.edlEmail.error = getString(R.string.warning_text_email_invalid)
                } else {
                    binding.edlEmail.error = null
                }
                DisplayLayout.buttonEnabled(
                    binding.buttonRegister,
                    binding.edtEmail,
                    this@RegisterActivity
                )
            }

            override fun afterTextChanged(text: Editable) {
                if (DisplayLayout.isValidEmail(text)) {
                    binding.edlEmail.endIconDrawable =
                        ContextCompat.getDrawable(this@RegisterActivity, R.drawable.check_circle)
                } else {
                    binding.edlEmail.endIconDrawable = null
                }
            }
        })

        binding.edtPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (text.toString().isEmpty()) {
                    binding.edtPhone.error = getString(R.string.warning_text_field_empty)
                } else if (!DisplayLayout.isValidPhoneNumber(text)) {
                    binding.edlPhone.error = getString(R.string.warning_text_phone_invalid)
                } else {
                    binding.edlEmail.error = null
                }
                DisplayLayout.buttonEnabled(
                    binding.buttonRegister,
                    binding.edtEmail,
                    this@RegisterActivity
                )
            }

            override fun afterTextChanged(text: Editable) {
                if (DisplayLayout.isValidPhoneNumber(text)) {
                    binding.edlEmail.endIconDrawable =
                        ContextCompat.getDrawable(this@RegisterActivity, R.drawable.check_circle)
                } else {
                    binding.edlEmail.endIconDrawable = null
                }
            }

        })

        /*password*/
        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (text.toString().isEmpty()) {
                    binding.edlPassword.error = getString(R.string.warning_text_field_empty)
                } else if (!DisplayLayout.containsUpperCase(text)) {
                    binding.edlPassword.error = getString(R.string.warning_password_uppercase)
                } else if (!DisplayLayout.containsSpecialCharacter(text)) {
                    binding.edlPassword.error =
                        getString(R.string.warning_password_special_character)
                } else {
                    binding.edlPassword.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }


    private fun navigation() {
        binding.apply {

            /*To OTP*/
            buttonRegister.setOnClickListener {
                val name = binding.edtName.text.toString()
                val email = binding.edtEmail.text.toString()
                val phone = binding.edtPhone.text.toString()
                val password = binding.edtPassword.text.toString()

                viewModel.register(name, email, phone, password)
                    .observe(this@RegisterActivity) { resources ->
                        when (resources.status) {
                            Status.LOADING -> {}
                            Status.SUCCESS -> {
                                val bundle = Bundle().apply {
                                    putString(KEY_NAME, name)
                                    putString(KEY_EMAIL, email)
                                    putString(KEY_PHONE, phone)
                                    putString(KEY_PASSWORD, password)
                                }
                                DisplayLayout.toastMessage(
                                    this@RegisterActivity,
                                    resources.message.toString(),
                                    true
                                )
                                val intent =
                                    Intent(this@RegisterActivity, OtpActivity::class.java).apply {
                                        putExtras(bundle)
                                    }
                                startActivity(intent)

                            }

                            Status.ERROR -> {
                                DisplayLayout.toastMessage(
                                    this@RegisterActivity,
                                    resources.message.toString(),
                                    false
                                )
                            }
                        }
                    }
            }
        }
    }

    companion object {
        const val KEY_NAME = "key_name"
        const val KEY_EMAIL = "key_email"
        const val KEY_PHONE = "key_phone"
        const val KEY_PASSWORD = "key_password"
    }
}