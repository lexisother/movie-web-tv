package dev.alyxia.movietv

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.alyxia.movietv.ui.screens.Home

@Composable
fun App() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "/") {
        composable("/") {
            Home(navController = navController)
        }
    }
}