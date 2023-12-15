package com.am.finalproject.ui.details

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.am.finalproject.R
import com.am.finalproject.adapter.detail.ViewPagerAdapter
import com.am.finalproject.data.remote.DataItemCourse
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.ActivityDetailsBinding
import com.am.finalproject.ui.details.materi.MateriKelasDetailsFragment
import com.am.finalproject.ui.details.tentang.TentangDetailsFragment
import com.am.finalproject.utils.DisplayLayout
import com.am.finalproject.utils.Formatter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val viewModel: DetailsViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DisplayLayout.hideAppBar(this)
        setupTabLayout()
        displayDetail()
    }

    private fun displayDetail() {
        val receiveBundle = intent.extras
        val id = receiveBundle?.getString(KEY_ID)

        Log.e("SIMPLE_ID", id.toString())
        viewModel.getDetailByIdCourse(id.toString()).observe(this) { resources ->
            when (resources.status) {
                Status.LOADING -> {
                    Log.e("SIMPLE_LOAD", "Load")
                }
                Status.SUCCESS -> {
                    val data = resources.data
                    setupData(data)
                }

                Status.ERROR -> {
                    Log.e("SIMPLE_ERROR", resources.message.toString())
                }
            }

        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun setupData(data: List<DataItemCourse>?) {
        data?.forEach { course ->
            Glide.with(binding.root.context).load(course.image).into(binding.imageViewContent)
            binding.tvCategoryTitle.text = course.category.title
            binding.tvTitle.text = course.title
            binding.tvCreator.text = course.authorBy
            binding.tvStar.text = course.rating.toString()
            binding.tvLevel.text = course.level
            if (!course.module.isNullOrEmpty()) {
                binding.tvTime.text =
                    Formatter.formatTimeSecondToMinute(course.module.sumOf { it.time ?: 0 })
                binding.tvModule.text = Formatter.formatSizeModule(course.module.size)
            }

            val button = findViewById<Button>(R.id.bt_telegram)
            val telegramGroup = course.telegram

            button.setOnClickListener {
                val uri = Uri.parse(telegramGroup)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                Log.e("SIMPLE_CLICK", "Clicked")
                intent.setPackage("org.telegram.messenger")
                startActivity(intent)
            }
        }
    }

    private fun setupTabLayout() {
        val fragments = ArrayList<Fragment>()
        fragments.add(TentangDetailsFragment())
        fragments.add(MateriKelasDetailsFragment())

        val titles = ArrayList<String>()
        titles.add("Tentang")
        titles.add("Materi Kelas")

        val pagerAdapter = ViewPagerAdapter(
            fragments, this
        )

        binding.viewPager2.isUserInputEnabled = false
        binding.viewPager2.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.middletab, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }


    companion object {
        const val KEY_ID = "key_id"
    }
}