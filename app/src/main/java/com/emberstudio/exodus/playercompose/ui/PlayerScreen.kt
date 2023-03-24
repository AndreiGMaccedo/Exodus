package com.emberstudio.exodus.playercompose.ui

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.doOnDetach
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.emberstudio.exodus.playercompose.PlayerHelper
import com.emberstudio.exodus.playercompose.PlayerViewModel
import com.emberstudio.exodus.utils.getMediaItem

@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel = hiltViewModel(),
    navController: NavController,
    selection: Int?
) {

    val context = LocalContext.current
    val exoPlayer = PlayerHelper.getInstance(context)

    val rawMediaUrl = viewModel.getVideoLink(selection ?: -1)
    
    Column(modifier = Modifier
        .background(Color.Black)
        .fillMaxSize(),

        verticalArrangement = Arrangement.Center) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
            PlayerView(it).apply {
                this.post {
                    this.player = exoPlayer
                    this.useController = true

                    if(rawMediaUrl.isBlank()) navController.popBackStack()

                    rawMediaUrl.let { rawUrl ->
                        Log.d("url", rawUrl)
                        val mediaItem = getMediaItem(rawUrl)

                        this.player?.setMediaItem(mediaItem)
                        this.player?.playWhenReady = true
                        this.player?.prepare()
                    }

                    this.doOnDetach {
                        exoPlayer.release()
                    }
                }
            }
        })
    }


}