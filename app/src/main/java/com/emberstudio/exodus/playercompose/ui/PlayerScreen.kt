package com.emberstudio.exodus.playercompose.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.doOnDetach
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.emberstudio.exodus.playercompose.PlayerHelper
import com.emberstudio.exodus.playercompose.PlayerViewModel

@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel = hiltViewModel(),
    navController: NavController,
    selection: Int?
) {

    val context = LocalContext.current
    val exoPlayer = PlayerHelper.getInstance(context)

    val youtubeUrl = "https://www.youtube.com/api/manifest/dash/id/bf5bb2419360daf1/source/youtube?as=fmp4_audio_clear,fmp4_sd_hd_clear&sparams=ip,ipbits,expire,source,id,as&ip=0.0.0.0&ipbits=0&expire=19000000000&signature=51AF5F39AB0CEC3E5497CD9C900EBFEAECCCB5C7.8506521BFC350652163895D4C26DEE124209AA9E&key=ik0"

    val rawMediaUrl = viewModel.getVideoLink(selection ?: -1)

    Column(modifier = Modifier
        .background(Color.Black)
        .fillMaxSize(),

        verticalArrangement = Arrangement.Center) {
        AndroidView(factory = {
            PlayerView(it).apply {
                this.post {
                    this.player = exoPlayer
                    this.useController = true

                    if(rawMediaUrl.isBlank()) navController.popBackStack()

                    rawMediaUrl.let { rawUrl ->
                        Log.d("url", rawUrl)
                        val mediaItem = if(rawUrl.contains("youtube")) {
                            MediaItem.Builder()
                                .setUri(rawUrl)
                                .setMimeType(MimeTypes.APPLICATION_MPD)
                                .build()
                        } else {
                            MediaItem.Builder().setUri(rawUrl).build()
                        }

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