package com.emberstudio.exodus.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.emberstudio.exodus.navigation.NavigationDirections
import com.emberstudio.exodus.navigation.PlayerNavigation

@Composable
fun HomeScreen(navController: NavController){

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        ClickableText(text = AnnotatedString("Audio Demo"), onClick = {
            navController.navigate(PlayerNavigation.player(0).destination)
        })

        Spacer(modifier = Modifier.height(24.dp))

        ClickableText(text = AnnotatedString("Video Demo"), onClick = {
            navController.navigate(PlayerNavigation.player(1).destination)
        })

        Spacer(modifier = Modifier.height(24.dp))

        ClickableText(text = AnnotatedString("Youtube Video Demo"), onClick = {
            navController.navigate(PlayerNavigation.player(2).destination)
        })
    }

}