package com.am.finalproject.ui.details.youtube

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.am.finalproject.databinding.ActivityYoutubeViewBinding
import com.am.finalproject.utils.Formatter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import java.util.regex.Pattern

class YoutubeViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityYoutubeViewBinding
    private var isFullscreen = false
    private lateinit var youTubePlayer: YouTubePlayer
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (isFullscreen) {
                // if the player is in fullscreen, exit fullscreen
                youTubePlayer.toggleFullscreen()
            } else {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYoutubeViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(onBackPressedCallback)

        val receiveBundleUrl = intent.getStringExtra(KEY_URL)
        Log.e("SIMPLE", receiveBundleUrl.toString())

        val youTubePlayerView = binding.youtubePlayerView
        val fullScreenContainer = binding.fullScreenContainer

        val iFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(1)
            .fullscreen(1)
            .build()

        youTubePlayerView.enableAutomaticInitialization = false

        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)

                val url = Formatter.extractYouTubeId(receiveBundleUrl.toString())
                youTubePlayer.loadVideo(url.toString(), 0F)
            }
        })

        youTubePlayerView.addFullscreenListener(object : FullscreenListener {
            override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                isFullscreen = true

                youTubePlayerView.visibility = View.GONE
                fullScreenContainer.visibility = View.VISIBLE
                fullScreenContainer.addView(fullscreenView)
            }

            override fun onExitFullscreen() {
                isFullscreen = false

                youTubePlayerView.visibility = View.VISIBLE
                fullScreenContainer.visibility = View.GONE
                fullScreenContainer.removeAllViews()
            }
        })

        youTubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                this@YoutubeViewActivity.youTubePlayer = youTubePlayer
                val videoId = extractYouTubeId(receiveBundleUrl)
                youTubePlayer.loadVideo(videoId, 0f)

                val fullscreenButton = binding.fullscreenButton
                fullscreenButton.setOnClickListener {
                    youTubePlayer.toggleFullscreen()
                }
            }
        }, iFramePlayerOptions)

        binding.imageViewBack.setOnClickListener {
            navigateBack()
        }
    }

    private fun extractYouTubeId(youtubeUrl: String?): String {
        //ekstraks ID video dari URL YouTube lengkap
        val pattern = Pattern.compile("https?://.*(?:youtu\\.be/|v/|e/|u/\\w+/|embed/|v=)([^#&?]*).*")
        val matcher = pattern.matcher(youtubeUrl.toString())

        return if (matcher.matches()) {
            matcher.group(1)!!
        } else {
            ""
        }
    }

    private fun navigateBack() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    companion object {
        const val KEY_URL = "key_url"
    }
}