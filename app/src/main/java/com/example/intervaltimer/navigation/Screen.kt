package com.example.intervaltimer.navigation

sealed class Screen (val route: String) {

    object MainScreen: Screen ("main")
    object AddNewTimerScreen: Screen ("create")
    object TimerScreen: Screen ("timer")
}