package com.emberstudio.exodustv.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.emberstudio.exodustv.ui.screens.CatalogBrowser
import com.emberstudio.exodustv.ui.screens.DetailsScreen

@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController, 
        startDestination = "/"
    ) {
        composable("/") {
            CatalogBrowser(
                onMovieSelected = { movie ->
                    navController.navigate("/movie/${movie.id}")
                }
            )
        }
        composable(
            route = "/movie/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong("id") ?: 0L
            DetailsScreen(
                movieId = movieId,
                backAction = {
                    navController.popBackStack()
                }
            )
        }
    }
}
