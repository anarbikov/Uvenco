package com.uvenco.navigation

sealed class Screens(val route: String) {
    object HomeScreen: Screens("home_screen")
    object Detail: Screens("Detail_screen")
}