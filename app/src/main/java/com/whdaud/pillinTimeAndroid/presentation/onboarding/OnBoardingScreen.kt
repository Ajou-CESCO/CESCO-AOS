package com.whdaud.pillinTimeAndroid.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whdaud.pillinTimeAndroid.R
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = hiltViewModel(),
    navController: NavController
) {
    val onBoardingImage = listOf(
        R.drawable.img_onboarding1,
        R.drawable.img_onboarding2,
        R.drawable.img_onboarding3
    )
    var currentIndex by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    Image(
        painter = painterResource(id = onBoardingImage[currentIndex]),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                onClick = {
                    coroutineScope.launch {
                        if (currentIndex == 2) {
                            viewModel.saveAppEntry(navController)
                        } else {
                            currentIndex = (currentIndex + 1) % onBoardingImage.size
                        }
                    }
                },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentScale = ContentScale.Crop
    )
}