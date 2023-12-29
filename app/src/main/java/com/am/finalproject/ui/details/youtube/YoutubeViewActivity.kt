package com.am.finalproject.ui.details.youtube

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.am.finalproject.databinding.ActivityYoutubeViewBinding
import com.am.finalproject.utils.Formatter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class YoutubeViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityYoutubeViewBinding
    private val isFullscreen = false
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

        val receiveBundleUrl = intent.getStringExtra(KEY_URL)
        Log.e("SIMPLE", receiveBundleUrl.toString())

        val youTubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)

                val url = Formatter.extractYouTubeId(receiveBundleUrl.toString())
                youTubePlayer.loadVideo(url.toString(), 0F)
            }
        })
    }

    companion object {
        const val KEY_URL = "key_url"
    }
}