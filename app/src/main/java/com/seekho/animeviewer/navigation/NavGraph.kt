package com.seekho.animeviewer.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seekho.animeviewer.view.AnimeDetailScreen
import com.seekho.animeviewer.view.AnimeListScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateNavigationGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screens.LIST) {

        composable(Screens.LIST) {
            AnimeListScreen(navController)
        }

        composable(Screens.DETAIL, arguments = listOf(navArgument("id") { type = NavType.IntType }))
        { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("id")
            AnimeDetailScreen(navController, animeId)
        }
    }
}
