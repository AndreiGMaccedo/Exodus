package com.emberstudio.exodus.home

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import com.emberstudio.exodus.navigation.NavigationDirections
import com.emberstudio.exodus.navigation.PlayerNavigation
import com.emberstudio.exodus.playertraditional.PlayerActivity

@Composable
@UnstableApi
fun HomeScreen(navController: NavController){

    var selectedVideo by remember{ mutableStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        ClickableText(text = AnnotatedString("Audio Demo"), onClick = {
            selectedVideo = 0
            showDialog = true
        })

        Spacer(modifier = Modifier.height(24.dp))

        ClickableText(text = AnnotatedString("Video Demo"), onClick = {
            selectedVideo = 1
            showDialog = true
        })

        Spacer(modifier = Modifier.height(24.dp))

        ClickableText(text = AnnotatedString("Youtube Video Demo"), onClick = {
            selectedVideo = 2
            showDialog = true
        })
    }

    if(showDialog) SelectionDialog(
        onDismiss = {
            showDialog = false
        },
        onOpenWithActivity = {
            showDialog = false
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtras(
                Bundle().apply {
                    this.putInt(PlayerNavigation.VIDEO_SELECTION, selectedVideo)
                }
            )
            context.startActivity(intent)
        },
        onOpenWithCompose = {
            showDialog = false
            navController.navigate(PlayerNavigation.player(selectedVideo).destination)
        }
    )


}

@Composable
fun SelectionDialog(
    onDismiss: () -> Unit,
    onOpenWithCompose: () -> Unit,
    onOpenWithActivity: () -> Unit
){
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true)
        ) {
        Column(modifier = Modifier.wrapContentSize().background(Color.Black)) {
            ClickableText(
                modifier = Modifier.padding(12.dp),
                style = TextStyle.Default.copy(
                    color = Color.White
                ),
                text = AnnotatedString("Compose Player"), onClick = { onOpenWithCompose.invoke() })
            Spacer(modifier = Modifier.height(8.dp))
            ClickableText(
                modifier = Modifier.padding(12.dp),
                style = TextStyle.Default.copy(
                    color = Color.White
                ),
                text = AnnotatedString("Activity Player"), onClick = { onOpenWithActivity.invoke() })
        }
    }
}