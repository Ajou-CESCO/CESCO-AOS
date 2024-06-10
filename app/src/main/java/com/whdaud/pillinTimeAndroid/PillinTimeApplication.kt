package com.whdaud.pillinTimeAndroid

import android.app.Application
import com.whdaud.pillinTimeAndroid.data.local.HealthConnectManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PillinTimeApplication: Application() {
    val healthConnectManager by lazy {
        HealthConnectManager(this)
    }
}