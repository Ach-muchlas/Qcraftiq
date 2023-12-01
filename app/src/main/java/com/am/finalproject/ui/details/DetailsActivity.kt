package com.am.finalproject.ui.details


import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.am.finalproject.databinding.ActivityDetailsBinding
import com.am.finalproject.ui.adapter.ViewPagerAdapter

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

        binding.viewPager2.isUserInputEnabled = false
        binding.viewPager2.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.middletab, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }
}