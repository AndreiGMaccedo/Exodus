package com.emberstudio.exodus.playercompose.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.media.session.MediaSession
import android.media.session.MediaSessionManager
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.doOnAttach
import androidx.core.view.doOnDetach
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.exoplayer.SeekParameters
import androidx.media3.ui.PlayerControlView
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.emberstudio.exodus.R
import com.emberstudio.exodus.playercompose.PlayerHelper
import com.emberstudio.exodus.playercompose.PlayerViewModel
import com.emberstudio.exodus.utils.getMediaItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel = hiltViewModel(),
    navController: NavController,
    selection: Int?
) {
    val rawMediaUrl = viewModel.getVideoLink(selection ?: -1)
    val context = LocalContext.current
    var playerTouched by rememberSaveable { mutableStateOf(true) }

    val millisInFuture = 3*1000L

    val countDownTimer =
        object : CountDownTimer(millisInFuture, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d("TAG", "onTick: ${(millisUntilFinished/1000L).seconds}")
            }

            override fun onFinish() {
                playerTouched = false
            }
        }

    val exoPlayer = remember {
        PlayerHelper.getInstance(context).apply {
            if(rawMediaUrl.isBlank()) navController.popBackStack()
            rawMediaUrl.let { rawUrl ->
                setMediaItem(getMediaItem(rawUrl))
                playWhenReady = true
                prepare()
            }
        }
    }

    LaunchedEffect(Unit){
        countDownTimer.start()
    }

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
        ) {
        Surface(modifier = Modifier) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = {
                    PlayerView(it).apply {
                        this.post {
                            this.player = exoPlayer
                            this.useController = false

                            this.setOnClickListener {
                                countDownTimer.cancel()
                                countDownTimer.start()
                                playerTouched = true
                            }

                            this.doOnDetach {
                                exoPlayer.release()
                            }
                        }
                    }
                })
        }
        PlayerControllerView(exoPlayer, playerTouched){
            navController.navigateUp()
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}