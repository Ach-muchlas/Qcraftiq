package com.am.finalproject.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.am.finalproject.R
import com.am.finalproject.data.remote.LoginResult
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.ActivityLoginBinding
import com.am.finalproject.ui.auth.AuthViewModel
import com.am.finalproject.ui.auth.register.RegisterActivity
import com.am.finalproject.ui.auth.reset_password.ResetPasswordActivity
import com.am.finalproject.ui.main.MainActivity
import com.am.finalproject.ui.onboarding.OnBoardingActivity
import com.am.finalproject.utils.DisplayLayout
import com.am.finalproject.utils.Navigate
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialization()
        setupEditText()
        navigation()
    }

    private fun initialization() {
        viewModel.init(this)
        DisplayLayout.hideAppBar(this)
    }

    private fun setupEditText() {
        /*email*/
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
                }
                DisplayLayout.buttonEnabled(
                    binding.buttonLogin,
                    binding.edtEmail,
                    this@LoginActivity
                )
            }

            override fun afterTextChanged(text: Editable) {
                if (DisplayLayout.isValidEmail(text)) {
                    binding.edlEmail.endIconDrawable =
                        ContextCompat.getDrawable(this@LoginActivity, R.drawable.check_circle)
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
        if (!viewModel.isOnBoardingCompleted()) {
            Navigate.intentActivity(this@LoginActivity, OnBoardingActivity::class.java)
            viewModel.markOnBoardingCompleted()
        }
        if (viewModel.isUserLogin()) Navigate.intentActivity(
            this@LoginActivity,
            MainActivity::class.java
        )

        binding.apply {
            /*To register*/
            textViewRegisterHere.setOnClickListener {
                Navigate.intentActivity(this@LoginActivity, RegisterActivity::class.java)
            }

            /*To Home*/
            textViewEnterWithoutLogin.setOnClickListener {
                Navigate.intentActivity(this@LoginActivity, MainActivity::class.java)
            }

            /*Reset Password*/
            textViewForgotPassword.setOnClickListener {
                Navigate.intentActivity(this@LoginActivity, ResetPasswordActivity::class.java)
            }

            /*Login*/
            buttonLogin.setOnClickListener {
                val emailOrPhone = binding.edtEmail.text.toString()
                val password = binding.edtPassword.text.toString()

                viewModel.login(emailOrPhone, password).observe(this@LoginActivity) { resources ->

                    when (resources.status) {
                        Status.LOADING -> {}

                        Status.SUCCESS -> {
                            viewModel.saveUser(LoginResult(resources.data?.data?.accessToken))
                            StyleableToast.makeText(
                                this@LoginActivity,
                                resources.data?.message,
                                Toast.LENGTH_SHORT,
                                R.style.MyToast_IsGreen
                            ).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }

                        Status.ERROR -> {
                            StyleableToast.makeText(
                                this@LoginActivity,
                                "Email or Phone not Registered",
                                Toast.LENGTH_SHORT,
                                R.style.MyToast_IsRed
                            ).show()
                            Log.e("CHECK", resources.data?.message.toString())
                        }
                    }
                }
            }


        }
    }
}
