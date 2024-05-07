package com.example.pillinTimeAndroid.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.example.pillinTimeAndroid.presentation.home.components.ClientListBar
import com.example.pillinTimeAndroid.presentation.home.components.DoseCard
import com.example.pillinTimeAndroid.presentation.home.components.HealthCard
import com.example.pillinTimeAndroid.ui.theme.Gray5
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val userDetails by viewModel.userDetails.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray5)
            .padding(horizontal = BasicPadding)
    ) {
        if (userDetails?.inManager == true) {
            ClientListBar()
            Spacer(modifier = Modifier.height(23.dp))
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 23.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val textStyle = PillinTimeTheme.typography.logo3Medium.copy(color = Gray90)
                val text = buildAnnotatedString {
                    withStyle(style = textStyle.toSpanStyle().copy(color = Primary60)) {
                        append(userDetails?.name.toString())
                    }
                    append("님,\n오늘 하루도 화이팅이에요!")
                }
                Text(
                    text = text,
                    style = textStyle
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_alert_on),
                    contentDescription = "",
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.height(29.dp))
        }
        Column(
            modifier = Modifier
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
        }
    }
}

//@Preview(
//    showSystemUi = true,
//    showBackground = true
//)
//@Composable
//private fun HomeScreenPreview() {
//    PillinTimeAndroidTheme {
//        HomeScreen()
//    }
//}