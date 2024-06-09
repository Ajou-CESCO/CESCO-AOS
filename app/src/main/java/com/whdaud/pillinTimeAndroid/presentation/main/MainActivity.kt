package com.whdaud.pillinTimeAndroid.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.whdaud.pillinTimeAndroid.presentation.nvgraph.NavGraph
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme
import com.whdaud.pillinTimeAndroid.ui.theme.White
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition(condition = { viewModel.splashCondition.value })
        }
        setContent {
            PillinTimeAndroidTheme {
                val systemUiColor = rememberSystemUiController()
                val appEntry by viewModel.appEntry.collectAsState()
                SideEffect {
                    systemUiColor.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = true
                    )
                    systemUiColor.setNavigationBarColor(
                        color = White
                    )
                }
                NavGraph(startDestination = viewModel.startDestination.value, appEntry = appEntry)
            }
        }
    }
}
