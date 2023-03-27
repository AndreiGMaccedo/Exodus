package com.emberstudio.exodus.playertraditional

import android.content.pm.ActivityInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
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
        prepareSystemUI()

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

    fun prepareSystemUI() {
        //Hides the ugly action bar at the top
        supportActionBar?.hide()

        //Hide the status bars
        WindowCompat.setDecorFitsSystemWindows(window, false)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
                hide(WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }

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