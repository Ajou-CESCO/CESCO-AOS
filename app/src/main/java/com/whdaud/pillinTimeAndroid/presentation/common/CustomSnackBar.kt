package com.whdaud.pillinTimeAndroid.presentation.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whdaud.pillinTimeAndroid.ui.theme.Gray100
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.whdaud.pillinTimeAndroid.ui.theme.Primary5
import com.whdaud.pillinTimeAndroid.ui.theme.shapes

@Composable
fun CustomSnackBar(
    snackbarHostState: SnackbarHostState,
    message: String
) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = {
            Snackbar(
                modifier = Modifier
                    .padding(vertical = 15.dp),
                containerColor = Gray100,
                shape = shapes.small
            ) {
                Text(
                    text = message,
                    color = Primary5,
                    style = PillinTimeTheme.typography.body1Medium
                )
            }
        }
    )
}