package com.example.pillinTimeAndroid

import android.app.Application
import com.example.pillinTimeAndroid.data.local.HealthConnectManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PillinTimeApplication: Application() {
    val healthConnectManager by lazy {
        HealthConnectManager(this)
    }
}