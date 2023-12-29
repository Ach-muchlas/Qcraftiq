package com.am.finalproject.ui.onboarding

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.am.finalproject.R
import com.am.finalproject.adapter.onboarding.IntroSliderAdapter
import com.am.finalproject.data.intro.IntroSlideData
import com.am.finalproject.databinding.ActivityOnboardingBinding
import com.am.finalproject.ui.auth.login.LoginActivity
import com.am.finalproject.ui.auth.register.RegisterActivity
import com.am.finalproject.utils.DisplayLayout
import com.am.finalproject.utils.Navigate

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var introSliderAdapter: IntroSliderAdapter
    private lateinit var indicatorContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DisplayLayout.hideAppBar(this)
        navigation()
        setSliderItems()
        setupIndicator()
        setCurrentIndicator(0)
    }

    private fun navigation() {
        binding.textViewLoginHere.setOnClickListener {
            Navigate.intentActivity(this, LoginActivity::class.java)
        }
    }

    private fun setSliderItems() {
        introSliderAdapter = IntroSliderAdapter(
            listOf(
                IntroSlideData(
                    "Belajar dari Pengalaman Terbaik!",
                    R.drawable.image_splash_one
                ),
                IntroSlideData(
                    "Belajar dari Praktisi Terbaik!",
                    R.drawable.image_splash_two
                ),
                IntroSlideData(
                    "Belajar darimana Saja!",
                    R.drawable.image_splash_three
                )
            )
        )

        val onBoardingViewPager = binding.onboardingViewPager
        onBoardingViewPager.adapter = introSliderAdapter
        onBoardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onBoardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
        findViewById<ImageButton>(R.id.btn_splash).setOnClickListener {
            if (onBoardingViewPager.currentItem + 1 < introSliderAdapter.itemCount) {
                onBoardingViewPager.currentItem += 1
            } else {
                Navigate.intentActivity(this, RegisterActivity::class.java)
            }
        }
    }

    private fun setupIndicator(){
        indicatorContainer = binding.indicatorsContainer
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                it.layoutParams = layoutParams
                indicatorContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int){
        val childCount = indicatorContainer.childCount
        for (i in 0 until childCount){
            val imageView = indicatorContainer.getChildAt(i) as ImageView
            if (i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }
}