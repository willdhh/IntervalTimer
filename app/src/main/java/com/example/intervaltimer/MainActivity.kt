package com.example.intervaltimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.intervaltimer.navigation.Navigation
import com.example.intervaltimer.ui.theme.IntervalTimerTheme
import com.example.intervaltimer.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            IntervalTimerTheme {
                val mainViewModel: MainViewModel by viewModels()

                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background
                ) {

                    Navigation(mainViewModel = mainViewModel)
                }
            }
        }
    }
}



