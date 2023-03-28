package com.emberstudio.exodus.playercompose.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.emberstudio.exodus.R
import com.emberstudio.exodus.playercompose.PlayerHelper
import com.emberstudio.exodus.ui.theme.Material200Blue
import com.emberstudio.exodus.ui.theme.Material500Blue
import com.emberstudio.exodus.ui.theme.Purple200
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit


@Preview
@Composable
@UnstableApi
fun PlayerControllerPreview(){

    val context = LocalContext.current
    PlayerControllerView(PlayerHelper.getInstance(context), true){

    }
}

@Composable
fun PlayerControllerView(player: ExoPlayer, touched : Boolean, onVideoFinished: () -> Unit) {

    val currentPosition = player.currentPosition

    var currentValue by remember { mutableStateOf(currentPosition.toFloat()) }
    var duration by remember { mutableStateOf(100f) }
    var isPlaying by remember { mutableStateOf(player.isPlaying) }

    LaunchedEffect(Unit){
        val listener = object : Player.Listener{
            override fun onIsPlayingChanged(isPlaying_: Boolean) {
                isPlaying = isPlaying_
                duration = player.contentDuration.toFloat()
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                if(playbackState == Player.STATE_ENDED){
                    onVideoFinished.invoke()
                }
            }
        }
        player.addListener(listener)
    }

    if (isPlaying) {
        LaunchedEffect(Unit) {
            while(true) {
                currentValue = player.currentPosition.toFloat()
                delay(1000 / 30)
            }
        }
    }

    AnimatedVisibility(
        visible = touched,
        enter = fadeIn(),
        exit = fadeOut()
        ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Material200Blue
                        )
                    )
                )
        ) {

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                Slider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    steps = 1000,
                    value = currentValue,
                    valueRange = 0f..duration,
                    onValueChange = {
                        currentValue = it
                    },
                    onValueChangeFinished = {
                        player.seekTo(currentValue.toLong())
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = Material200Blue,
                        activeTickColor = Material200Blue,
                        activeTrackColor = Color.White
                    )

                    )
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                    Text(
                        text = String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(player.currentPosition) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(player.currentPosition)),
                            TimeUnit.MILLISECONDS.toSeconds(player.currentPosition) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(player.currentPosition))
                        ),
                        color = Color.White,
                        style = TextStyle.Default.copy(
                            fontFamily = FontFamily(Font(R.font.exo2_regular))
                        )
                    )

                    Text(
                        text = String.format("%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(duration.toLong()),
                            TimeUnit.MILLISECONDS.toSeconds(duration.toLong()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration.toLong()))
                        ),
                        color = Color.White,
                        style = TextStyle.Default.copy(
                            fontFamily = FontFamily(Font(R.font.exo2_regular))
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 36.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_rewind),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(36.dp)
                            .clickable {
                                player.seekBack()
                            },
                        tint = Color.White
                    )
                    IconToggleButton(checked = isPlaying, onCheckedChange = {
                        isPlaying = it
                    }) {
                        Icon(
                            painter = if (isPlaying) {
                                painterResource(id = R.drawable.ic_pause)
                            } else {
                                painterResource(id = R.drawable.ic_play)
                            },
                            modifier = Modifier.clickable {
                                if (isPlaying) {
                                    player.pause()
                                } else {
                                    player.play()
                                }
                            },
                            tint = Color.White,
                            contentDescription = ""
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_fforward),
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(36.dp)
                            .clickable {
                                player.seekForward()
                            }
                    )
                }
            }

        }
    }
}