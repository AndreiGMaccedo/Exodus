package com.emberstudio.exodus

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.emberstudio.exodus.navigation.NavigationDirections
import com.emberstudio.exodus.navigation.PlayerNavigation
import com.emberstudio.exodus.ui.theme.ExodusTheme
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()
            val navController = rememberNavController()
            val context = LocalContext.current

            val showTopBar = rememberSaveable { mutableStateOf(true) }
            val showBottomBar = rememberSaveable { mutableStateOf(true) }
            val selectedPage = rememberSaveable { mutableStateOf(0) }


            val navBackStackEntry = navController.currentBackStackEntryAsState()

            when (navBackStackEntry.value?.destination?.route) {
                PlayerNavigation.route -> {
                    showTopBar.value = false
                    showBottomBar.value = false
                    this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    hideSystemUI()
                }
                else -> {
                    showTopBar.value = true
                    showBottomBar.value = true
                    this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                    showSystemUi()
                }
            }

            Scaffold(
                modifier = Modifier,
                scaffoldState = scaffoldState,
                topBar = {
                    AnimatedVisibility(
                        visible = showTopBar.value,
                        enter = slideInVertically(initialOffsetY = { -it }),
                        exit = slideOutVertically(targetOffsetY = { -it })
                    ) {
                        TopAppBar(modifier = Modifier) {
                            Text(text = "Exodus")
                        }
                    }
                },
                bottomBar = {
                    AnimatedVisibility(
                        visible = showTopBar.value,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it })
                    ) {
                        BottomNavigation(modifier = Modifier.wrapContentSize()) {
                            navItems.forEachIndexed { index, navItem ->
                                BottomNavigationItem(
                                    selected = index == selectedPage.value,
                                    selectedContentColor = Color.White,
                                    unselectedContentColor = Color.Gray,
                                    onClick = {
                                        selectedPage.value = index
                                        navController.navigate(navItem.destination, navOptions {
                                            launchSingleTop = true
                                            popUpTo(navController.graph.id){
                                                inclusive = true
                                            }
                                        })
                                    },
                                    label = { Text(text = stringResource(id = navItem.label)) },
                                    icon = { Icon(painter = painterResource(id = navItem.icon), contentDescription = "") }
                                )

                            }
                        }
                    }
                }
            ) { paddingValues ->
                ExodusApp(navController)
            }
        }
    }


    fun showSystemUi(){
        //Hides the ugly action bar at the top
        actionBar?.show()

        //Hide the status bars
        WindowCompat.setDecorFitsSystemWindows(window, true)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        } else {
            window.insetsController?.apply {
                show(WindowInsets.Type.statusBars())
                show(WindowInsets.Type.navigationBars())
            }
        }
    }
    fun hideSystemUI() {
        //Hides the ugly action bar at the top
        actionBar?.hide()

        //Hide the status bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
                hide(WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }

    }
}
