package com.example.pillinTimeAndroid.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.example.pillinTimeAndroid.presentation.btmnavigator.BottomNavigator
import com.example.pillinTimeAndroid.presentation.home.components.ClientListBar
import com.example.pillinTimeAndroid.presentation.home.components.DoseCard
import com.example.pillinTimeAndroid.presentation.home.components.HealthCard
import com.example.pillinTimeAndroid.ui.theme.Gray5
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun HomeScreen() {
    val isManager by remember { mutableStateOf(true) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.background(color = Gray5)
    ) {
        if (isManager) {
            ClientListBar()

        } else {

        }
        Spacer(modifier = Modifier.height(23.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = BasicPadding)
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .clip(shapes.small)
                    .background(White)
            ) {
                DoseCard()
            }
            Spacer(modifier = Modifier.height(23.dp))
            Column(
                modifier = Modifier
                    .clip(shapes.small)
                    .background(White)
            ) {
                HealthCard()
            }
            Spacer(modifier = Modifier.height(106.dp))
            Column(
                modifier = Modifier
                    .clip(shapes.small)
                    .background(White)
            ) {
                DoseCard()
            }
            Column(
                modifier = Modifier
                    .clip(shapes.small)
                    .background(White)
            ) {
                DoseCard()
            }
            Column(
                modifier = Modifier
                    .clip(shapes.small)
                    .background(White)
            ) {
                DoseCard()
            }
        }
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun HomeScreenPreview() {
    PillinTimeAndroidTheme {
        HomeScreen()
    }
}