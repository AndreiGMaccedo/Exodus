package com.emberstudio.exodustv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.core.view.WindowCompat
import androidx.tv.material3.Surface
import com.emberstudio.exodustv.ui.App
import com.emberstudio.exodustv.ui.theme.ExodusTVTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Loads [MainFragment].
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ExodusTVTheme {
                Surface(
                    shape = RectangleShape,
                    modifier = Modifier.fillMaxSize()
                ){
                    App()
                }
            }
        }
    }
}