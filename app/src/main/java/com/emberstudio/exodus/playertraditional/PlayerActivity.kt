package com.emberstudio.exodus.playertraditional

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.emberstudio.exodus.R
import com.emberstudio.exodus.databinding.ActivityPlayerBinding
import com.emberstudio.exodus.navigation.PlayerNavigation
import com.emberstudio.exodus.playercompose.PlayerHelper
import com.emberstudio.exodus.playercompose.PlayerViewModel
import com.emberstudio.exodus.utils.getMediaItem

@UnstableApi class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding

    private val viewModel: PlayerViewModel by viewModels()

    private var rawUrl: String? = null
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Exodus_AppCompat)
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selection = intent.extras?.getInt(PlayerNavigation.VIDEO_SELECTION, -1) ?: -1
        rawUrl = viewModel.getVideoLink(selection)

        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        exoPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }

    private fun initializePlayer(){
        exoPlayer = PlayerHelper.getInstance(this@PlayerActivity)
        val playerView = binding.exodusPlayer
        playerView.player = exoPlayer

        playerView.useController = true

        rawUrl?.let {
            val mediaItem = getMediaItem(it)

            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.playWhenReady = true
            exoPlayer.prepare()
        }
    }
}