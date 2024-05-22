package com.uvenco.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.uvenco.ui.characterDetails.DetailsScreen
import com.uvenco.ui.home.HomeScreen
import com.uvenco.ui.home.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph (navController: NavHostController,viewModel: HomeViewModel){
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen .route)
    {
        composable(route = Screens.HomeScreen .route){
            HomeScreen(viewModel, navController = navController)
        }
        composable(route = Screens.Detail.route){
            DetailsScreen(viewModel)
        }
    }
}