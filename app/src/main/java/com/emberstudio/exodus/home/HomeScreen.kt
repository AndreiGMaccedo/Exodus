package com.emberstudio.exodus.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.navigation.NavController
import com.emberstudio.exodus.navigation.NavigationDirections

@Composable
fun HomeScreen(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        ClickableText(text = AnnotatedString("Click here to play a demo video"), onClick = {
            navController.navigate(NavigationDirections.player.destination)
        })
    }

}