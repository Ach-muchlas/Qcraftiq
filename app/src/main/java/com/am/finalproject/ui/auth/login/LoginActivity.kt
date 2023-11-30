package com.am.finalproject.ui.auth.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.am.finalproject.databinding.ActivityLoginBinding
import com.am.finalproject.ui.auth.register.RegisterActivity
import com.am.finalproject.ui.main.MainActivity
import com.am.finalproject.utils.DisplayLayout
import com.am.finalproject.utils.Navigate

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setupView()
        navigation()
    }

    private fun setupView() {
        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                DisplayLayout.validateEmailEditText(
                    binding.edtEmail,
                    text,
                    this@LoginActivity
                )
                DisplayLayout.buttonEnabled(
                    binding.buttonLogin,
                    binding.edtEmail,
                    this@LoginActivity
                )
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                DisplayLayout.validatePasswordEditText(
                    binding.edtPassword,
                    text,
                    this@LoginActivity
                )
            }

            override fun afterTextChanged(p0: Editable?) {}
        })


    }

    private fun navigation() {
        binding.textViewRegisterHere.setOnClickListener {
            Navigate.intentActivity(this, RegisterActivity::class.java)
        }
        binding.textViewEnterWithoutLogin.setOnClickListener {
            Navigate.intentActivity(this, MainActivity::class.java)
        }
    }
}
