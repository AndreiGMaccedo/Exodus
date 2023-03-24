package com.emberstudio.exodus

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emberstudio.exodus.about.AboutScreen
import com.emberstudio.exodus.home.HomeScreen
import com.emberstudio.exodus.navigation.NavigationDirections
import com.emberstudio.exodus.navigation.PlayerNavigation
import com.emberstudio.exodus.playercompose.ui.PlayerScreen
import com.emberstudio.exodus.ui.theme.ExodusTheme

data class NavItem(
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    val destination: String
)

val navItems = buildList {
    add(
        NavItem(
            icon = R.drawable.ic_home,
            label = R.string.home,
            NavigationDirections.home.destination
        )
    )
    add(
        NavItem(
            icon = R.drawable.ic_info,
            label = R.string.about,
            NavigationDirections.about.destination
        )
    )
}

@Composable
fun ExodusApp(navController: NavHostController) {
    ExodusTheme {
        NavHost(
            navController = navController,
            startDestination = NavigationDirections.home.destination
        ){
            composable(NavigationDirections.home.destination){
                HomeScreen(navController)
            }
            composable(NavigationDirections.about.destination){
                AboutScreen(navController)
            }
            composable(
                route = PlayerNavigation.route,
                arguments = PlayerNavigation.arguments)
            {
                val selection = it.arguments?.getInt(PlayerNavigation.VIDEO_SELECTION, -1)
                PlayerScreen(
                    navController = navController,
                    selection = selection)
            }
        }
    }
}

