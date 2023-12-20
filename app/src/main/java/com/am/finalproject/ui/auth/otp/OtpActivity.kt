package com.am.finalproject.ui.auth.otp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.am.finalproject.R
import com.am.finalproject.data.remote.LoginResult
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.ActivityOtpBinding
import com.am.finalproject.ui.auth.AuthViewModel
import com.am.finalproject.ui.auth.register.RegisterActivity
import com.am.finalproject.ui.main.MainActivity
import com.am.finalproject.utils.DisplayLayout
import com.am.finalproject.utils.Navigate
import io.github.muddz.styleabletoast.StyleableToast
import org.koin.android.ext.android.inject

class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding
    private val viewModel: AuthViewModel by inject()
    private val ediTextList by lazy {
        listOf(
            binding.edtOtp1,
            binding.edtOtp2,
            binding.edtOtp3,
            binding.edtOtp4,
            binding.edtOtp5,
            binding.edtOtp6
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DisplayLayout.hideAppBar(this)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigation()
        setupEditText()
    }

    private fun navigation() {
        val receivedBundle = intent.extras
        val email = receivedBundle?.getString(RegisterActivity.KEY_EMAIL)

        Log.e("SIMPLE", email.toString())
        binding.imageViewButtonBack.setOnClickListener {
            Navigate.intentActivity(this, RegisterActivity::class.java)
        }
        binding.textViewRequestNewCode.setOnClickListener {
            viewModel.resendOTP(email.toString()).observe(this) { resources ->
                when (resources.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        StyleableToast.makeText(
                            this,
                            resources.data?.message,
                            Toast.LENGTH_SHORT,
                            R.style.MyToast_IsGreen
                        ).show()
                    }

                    Status.ERROR -> {
                        StyleableToast.makeText(
                            this,
                            resources.data?.message,
                            Toast.LENGTH_SHORT,
                            R.style.MyToast_IsRed
                        ).show()
                    }
                }
            }
        }
    }

    /*function ini digunakan untuk setup focus edittext */
    /*biar pada saat user melakukan pengisian value edittext bisa langsung berpindah dari satu edit text
     ke editext lain*/
    private fun setupEditText() {
        for (i in ediTextList.indices) {
            ediTextList[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                    if (text.length == 1) {
                        if (i < ediTextList.size - 1) {
                            ediTextList[i + 1].requestFocus()
                        } else {
                            setupPost()
                            clearAllEditText()
                            ediTextList[0].requestFocus()
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
        }
    }

    /*function ini digunakan untuk setup data yang akan dikirim ke server */
    private fun setupPost() {
        val receivedBundle = intent.extras
        val name = receivedBundle?.getString(RegisterActivity.KEY_NAME)
        val email = receivedBundle?.getString(RegisterActivity.KEY_EMAIL)
        val phone = receivedBundle?.getString(RegisterActivity.KEY_PHONE)
        val password = receivedBundle?.getString(RegisterActivity.KEY_PASSWORD)


        viewModel.sendOTP(
            name.toString(),
            email.toString(),
            phone.toString(),
            password.toString(),
            getEnteredOTP()
        ).observe(this) { resources ->
            viewModel.loginUser(email.toString(), password.toString()).observe(this) { result ->
                when (resources.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        viewModel.init(this)
                        viewModel.saveUser(LoginResult(result.data?.data?.accessToken))
                        StyleableToast.makeText(
                            this,
                            resources.data?.message,
                            Toast.LENGTH_SHORT,
                            R.style.MyToast_IsGreen
                        ).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }

                    Status.ERROR -> {
                        StyleableToast.makeText(
                            this,
                            resources.data?.message,
                            Toast.LENGTH_SHORT,
                            R.style.MyToast_IsRed
                        ).show()
                    }
                }
            }
        }
    }


    /*function ini digunakan untuk menggabungkan value dari edit text menjadi satu*/
    private fun getEnteredOTP(): String {
        val enteredOTP = StringBuilder()
        for (i in ediTextList) {
            enteredOTP.append(i.text)
        }
        return enteredOTP.toString()
    }

    /*function ini digunakan untuk menghapus value edit text*/
    private fun clearAllEditText() {
        for (i in ediTextList) {
            i.text.clear()
        }
    }
}