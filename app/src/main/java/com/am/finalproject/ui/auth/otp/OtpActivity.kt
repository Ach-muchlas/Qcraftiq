package com.am.finalproject.ui.auth.otp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.ActivityOtpBinding
import com.am.finalproject.ui.auth.AuthViewModel
import com.am.finalproject.ui.auth.register.RegisterActivity
import com.am.finalproject.ui.main.MainActivity
import com.am.finalproject.utils.DisplayLayout
import com.am.finalproject.utils.Navigate
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
        binding.imageViewButtonBack.setOnClickListener {
            Navigate.intentActivity(this, RegisterActivity::class.java)
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
            when (resources.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    DisplayLayout.toastMessage(this, resources.message.toString())
                    Navigate.intentActivity(this, MainActivity::class.java)
                }

                Status.ERROR -> {
                    DisplayLayout.toastMessage(this, resources.message.toString())
                }
            }
        }

        viewModel.resendOTP(email.toString()).observe(this) { resources ->
            when (resources.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {}
                Status.ERROR -> {}
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