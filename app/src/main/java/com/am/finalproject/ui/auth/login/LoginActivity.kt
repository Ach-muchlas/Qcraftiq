package com.am.finalproject.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.am.finalproject.R
import com.am.finalproject.data.local.sharepref.UserPreferences
import com.am.finalproject.data.remote.LoginResult
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.ActivityLoginBinding
import com.am.finalproject.ui.auth.AuthViewModel
import com.am.finalproject.ui.auth.register.RegisterActivity
import com.am.finalproject.ui.main.MainActivity
import com.am.finalproject.utils.DisplayLayout
import com.am.finalproject.utils.Navigate
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userPreferences: UserPreferences

    private val viewModel: AuthViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userPreferences = UserPreferences(this)
        supportActionBar?.hide()
        setupEditText()
        navigation()
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
        if (userPreferences.isUserLogin()) Navigate.intentActivity(
            this@LoginActivity,
            MainActivity::class.java
        )

        binding.apply {
            /*To register*/
            textViewRegisterHere.setOnClickListener {
                Navigate.intentActivity(this@LoginActivity, RegisterActivity::class.java)
            }
        }
        /*To Home*/
        binding.textViewEnterWithoutLogin.setOnClickListener {
            Navigate.intentActivity(this@LoginActivity, MainActivity::class.java)
        }

        /*Login*/
        binding.buttonLogin.setOnClickListener {
            val emailOrPhone = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            viewModel.login(emailOrPhone, password).observe(this) { resources ->
                when (resources.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        userPreferences.saveUser(LoginResult(resources.data?.data?.accessToken))
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    }

                    Status.ERROR -> {
                        DisplayLayout.toastMessage(this, resources.message.toString())
                    }
                }

            }
            Log.e("CHECK", viewModel.login(emailOrPhone, password).toString())
        }
    }
}
