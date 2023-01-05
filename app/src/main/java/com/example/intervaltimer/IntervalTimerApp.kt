package com.example.intervaltimer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class IntervalTimerApp:Application() {
    override fun onCreate() {
        super.onCreate()
    }
}