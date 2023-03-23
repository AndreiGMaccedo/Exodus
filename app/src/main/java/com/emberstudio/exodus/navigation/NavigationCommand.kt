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

    private val VIDEO_URL = "video_url"
    val route = "${MainNavigation.PLAYER.name}/{$VIDEO_URL}"

    val arguments = listOf(
        navArgument(VIDEO_URL) { type = NavType.StringType }
    )

    fun player( videoUrl: String? = null ) =
        object : NavigationCommand {
            override val arguments = this@PlayerNavigation.arguments
            override val destination: String = "${MainNavigation.PLAYER.name}/$videoUrl"
        }

}


enum class MainNavigation{
    HOME,
    ABOUT,
    PLAYER
}