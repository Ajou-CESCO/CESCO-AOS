package com.example.pillinTimeAndroid.presentation.nvgraph

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.pillinTimeAndroid.ui.theme.White
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.Dimens.BottomNavSize
import com.example.pillinTimeAndroid.ui.theme.Gray30
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.PillinTimeAndroidTheme

@Composable
fun BottomNavigationBar(
    items: List<BottomNavigationItem>,
    selected: Int,
    onItemClick: (Int) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .height(BottomNavSize)
            .fillMaxWidth(),
        containerColor = White,
    ) {
        items.forEachIndexed { index, item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clickable(
                        onClick = { onItemClick(index) },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = item.icon),
                    contentDescription = null,
                    tint = if (index == selected) Gray70 else Gray30,
                )
            }
        }
    }
}

data class BottomNavigationItem(
    @DrawableRes val icon: Int
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BottomNavigationBarPreview() {
    PillinTimeAndroidTheme {
        BottomNavigationBar(
            items = listOf(
                BottomNavigationItem(icon = R.drawable.ic_tab_schedule),
                BottomNavigationItem(icon = R.drawable.ic_tab_home),
                BottomNavigationItem(icon = R.drawable.ic_tab_mypage),
            ),
            selected = 0,
            onItemClick = {}
        )
    }
}