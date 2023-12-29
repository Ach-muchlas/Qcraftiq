package com.am.finalproject.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.am.finalproject.R
import com.am.finalproject.adapter.detail.ViewPagerAdapter
import com.am.finalproject.data.remote.DataItemCourse
import com.am.finalproject.data.remote.DataItemModule
import com.am.finalproject.data.source.Status
import com.am.finalproject.databinding.ActivityDetailsBinding
import com.am.finalproject.ui.details.materi.MateriKelasDetailsFragment
import com.am.finalproject.ui.details.tentang.TentangDetailsFragment
import com.am.finalproject.utils.DisplayLayout
import com.am.finalproject.utils.Formatter
import com.am.finalproject.utils.Formatter.urlEncode
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
        displayDetail()
        setupTabLayout()
        navigation()
    }

    private fun navigation() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun displayDetail() {
        val receiveBundle = intent.extras
        val id = receiveBundle?.getString(KEY_ID)
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


    private fun setupData(data: List<DataItemCourse>?) {
        data?.forEach { course ->

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

            val youTubePlayerView = binding.youTubePlayerView
            lifecycle.addObserver(youTubePlayerView)
            youTubePlayerView.addYouTubePlayerListener(object: AbstractYouTubePlayerListener(){
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)

                    val module: DataItemModule? = course.module?.getOrNull(0)
                    val urlVideo = Formatter.extractYouTubeId(module?.video ?: "")

                    youTubePlayer.loadVideo(urlVideo.toString(), 0F)
                }
            })
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