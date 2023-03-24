package com.emberstudio.exodus.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface NavigationCommand {
    val arguments: List<NamedNavArgument>
    val destination: String
}

object NavigationDirections {
    val home = object : NavigationCommand {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
        override val destination: String = MainNavigation.HOME.name
    }

    val about = object : NavigationCommand {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
        override val destination: String = MainNavigation.ABOUT.name
    }

    val player = object : NavigationCommand {
        override val arguments: List<NamedNavArgument>
            get() = emptyList()
        override val destination: String = MainNavigation.PLAYER.name
    }
}

object PlayerNavigation{

    const val VIDEO_URL = "video_url"
    const val VIDEO_SELECTION = "video_selection"

    val route = "${MainNavigation.PLAYER.name}/{$VIDEO_SELECTION}"

    val arguments = listOf(
        navArgument(VIDEO_SELECTION) { type = NavType.IntType }
    )

    fun player( selection: Int ) =
        object : NavigationCommand {
            override val arguments = this@PlayerNavigation.arguments
            override val destination: String = "${MainNavigation.PLAYER.name}/${selection}"
        }
}


enum class MainNavigation{
    HOME,
    ABOUT,
    PLAYER
}