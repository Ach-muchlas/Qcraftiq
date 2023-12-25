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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
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
        youtubeVideo()
    }

    private fun displayDetail() {
        val receiveBundle = intent.extras
        val id = receiveBundle?.getString(KEY_ID)

        Log.e("SIMPLE_ID", id.toString())
        viewModel.getDetailByIdCourse(id.toString()).observe(this) { resources ->
            when (resources.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    val data = resources.data
                    setupData(data)
                }

                Status.ERROR -> {
                }
            }

        }
    }

    private fun youtubeVideo(){
        val youTubePlayerView = binding.youTubePlayerView
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.enableAutomaticInitialization = false

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = "ixOd42SEUF0"
                youTubePlayer.loadVideo(videoId, 0F)
            }
        })
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
                intent.setPackage("org.telegram.messenger")
                startActivity(intent)
            }
        }
    }


    private fun setupTabLayout() {
        val receiveBundle = intent.extras
        val id = receiveBundle?.getString(KEY_ID)
        val fragments = ArrayList<Fragment>()
        fragments.add(TentangDetailsFragment())
        fragments.add(MateriKelasDetailsFragment.newInstance(id.toString()))

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