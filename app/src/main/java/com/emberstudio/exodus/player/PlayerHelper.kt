package com.emberstudio.exodus.player

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer

object PlayerHelper {

    private lateinit var playerInstance : ExoPlayer

    fun getInstance(context: Context) : ExoPlayer{
        playerInstance = ExoPlayer.Builder(context).build()
        return playerInstance
    }

}