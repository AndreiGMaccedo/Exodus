package com.emberstudio.exodus.playercompose

import android.content.Context
import androidx.media3.common.C.VIDEO_SCALING_MODE_SCALE_TO_FIT
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer

@UnstableApi
object PlayerHelper {

    private lateinit var playerInstance : ExoPlayer

    private const val SEEK_INCREMENT = 15 * 1000L

    fun getInstance(context: Context) : ExoPlayer{
        playerInstance = ExoPlayer.Builder(context)
            .setSeekBackIncrementMs(SEEK_INCREMENT)
            .setSeekForwardIncrementMs(SEEK_INCREMENT)
            .setVideoScalingMode(VIDEO_SCALING_MODE_SCALE_TO_FIT)
            .build()
        return playerInstance
    }

}