package com.emberstudio.exodus.player.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.emberstudio.exodus.player.PlayerHelper

@Composable
fun PlayerScreen() {

    val context = LocalContext.current
    val exoPlayer = PlayerHelper.getInstance(context)

    AndroidView(factory = {
        PlayerView(it).apply {
            this.player = exoPlayer
        }
    })

}