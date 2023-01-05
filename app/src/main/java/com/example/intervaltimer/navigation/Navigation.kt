package com.example.intervaltimer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.intervaltimer.screens.AddNewTimerScreen
import com.example.intervaltimer.screens.MainScreen
import com.example.intervaltimer.screens.TimerScreen
import com.example.intervaltimer.viewmodel.MainViewModel
import com.example.intervaltimer.viewmodel.MainViewModelAbstract


@Composable
fun Navigation(mainViewModel: MainViewModelAbstract) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreen(
                navController,
                mainViewModel
            )

        }
        composable("create") {
            AddNewTimerScreen(navController, mainViewModel = mainViewModel)
        }
        composable("timer") {
            TimerScreen(
                totalTime = 100000L,
                navController = navController
            )
        }
    }
}
