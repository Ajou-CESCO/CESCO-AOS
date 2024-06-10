package com.whdaud.pillinTimeAndroid.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whdaud.pillinTimeAndroid.R
import com.whdaud.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.whdaud.pillinTimeAndroid.ui.theme.Gray40
import com.whdaud.pillinTimeAndroid.ui.theme.Gray90
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.whdaud.pillinTimeAndroid.ui.theme.White

@Composable
fun ClientListBar(
    modifier: Modifier = Modifier,
    profiles: List<String>,
    selectedIndex: Int,
    onProfileSelected: (Int) -> Unit
) {
    var selectedProfile by remember { mutableStateOf(profiles.getOrNull(0)) }

    if (profiles.isEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .height(76.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "등록된 피보호자가 없어요",
                color = Gray40,
                style = PillinTimeTheme.typography.caption2Bold
            )
        }
    } else {
        LazyRow(
            modifier = modifier
                .background(White),
            contentPadding = PaddingValues(start = BasicPadding, end = 6.dp)
        ) {
            items(profiles.size) { index ->
                val profile = profiles[index]
                val isSelected = index == selectedIndex
                val selectedType =
                    if (isSelected) PillinTimeTheme.typography.caption2Bold else PillinTimeTheme.typography.caption2Regular
                val selectedIcon =
                    if (isSelected) R.drawable.ic_client_filled else R.drawable.ic_client_unfilled
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                selectedProfile = profile
                                onProfileSelected(index)
                            },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                        .background(color = Color.Transparent)
                        .padding(top = 8.dp, bottom = 8.dp, end = 20.dp)
                ) {
                    Image(
                        painter = painterResource(id = selectedIcon),
                        contentDescription = "Profile Picture",
                    )
                    Text(
                        text = profile,
                        style = selectedType,
                        color = Gray90
                    )
                }
            }
        }
    }
}