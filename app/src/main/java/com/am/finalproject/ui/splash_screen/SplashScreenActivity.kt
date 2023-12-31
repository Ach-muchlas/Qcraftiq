package com.am.finalproject.ui.splash_screen

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.media.metrics.PlaybackSession
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.am.finalproject.databinding.ActivitySplashScreenBinding
import com.am.finalproject.ui.auth.AuthViewModel
import com.am.finalproject.ui.auth.login.LoginActivity
import com.am.finalproject.ui.main.MainActivity
import com.am.finalproject.ui.onboarding.OnBoardingActivity
import com.am.finalproject.utils.DisplayLayout
import com.am.finalproject.utils.Navigate
import org.koin.android.ext.android.inject

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val viewModel: AuthViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
//        playAnimation()
        navigation()
    }

    private fun initialize() {
        DisplayLayout.hideAppBar(this)
        viewModel.init(this)
    }


    private fun navigation() {
        Handler().postDelayed({
            if (!viewModel.isOnBoardingCompleted()) {
                Navigate.intentActivity(this, OnBoardingActivity::class.java)
                viewModel.markOnBoardingCompleted()
            } else if (viewModel.isUserLogin()) {
                Navigate.intentActivity(
                    this,
                    MainActivity::class.java
                )
            } else {
                val mainIntent = Intent(this, LoginActivity::class.java)
                startActivity(mainIntent)
                finish()
            }
        }, 2000L)
    }

    private fun playAnimation() {
        val logo = ObjectAnimator.ofFloat(binding.imageViewAppLogo, View.ALPHA, 1f).setDuration(2000)
        val name = ObjectAnimator.ofFloat(binding.textViewStudy, View.ALPHA, 1f).setDuration(2000)

        AnimatorSet().apply {
            playSequentially(logo, name)
        }.start()
    }
}