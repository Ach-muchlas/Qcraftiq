package com.am.finalproject.ui.details

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.am.finalproject.R
import com.am.finalproject.databinding.ActivityDetailsBinding
import com.am.finalproject.ui.adapter.ViewPagerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.google.android.material.tabs.TabLayoutMediator

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragments = ArrayList<Fragment>()
        fragments.add(TentangDetailsFragment())
        fragments.add(MateriKelasDetailsFragment())

        val titles = ArrayList<String>()
        titles.add("Tentang")
        titles.add("Materi Kelas")

        val pagerAdapter = ViewPagerAdapter(
            fragments,
            this
        )

        val bottomSheetDialogFragment = BottomSheetDialogFragment()
        bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)

        binding.viewPager2.isUserInputEnabled = false
        binding.viewPager2.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.middletab, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()

        // Add this block to load a webpage in your WebView
        val myWebView: WebView = findViewById(R.id.webView)
        myWebView.loadUrl("https://www.example.com")
        myWebView.settings.javaScriptEnabled = true
    }
}