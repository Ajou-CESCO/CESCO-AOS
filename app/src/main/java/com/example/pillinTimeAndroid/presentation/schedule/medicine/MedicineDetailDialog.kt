package com.example.pillinTimeAndroid.presentation.schedule.medicine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.data.remote.dto.MedicineDTO
import com.example.pillinTimeAndroid.presentation.Dimens
import com.example.pillinTimeAndroid.presentation.Dimens.AlertDialogPadding
import com.example.pillinTimeAndroid.presentation.Dimens.SmallSize
import com.example.pillinTimeAndroid.presentation.common.ButtonColor
import com.example.pillinTimeAndroid.presentation.common.ButtonSize
import com.example.pillinTimeAndroid.presentation.common.CustomButton
import com.example.pillinTimeAndroid.ui.theme.Gray40
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineDetailDialog(
    medicineInfo: MedicineDTO,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    val scrollState = rememberScrollState()

    BasicAlertDialog(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.BasicPadding, vertical = 68.dp),
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shapes.medium)
                .background(White)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = AlertDialogPadding)
                    .verticalScroll(scrollState)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(medicineInfo.medicineImage)
                        .crossfade(true)
                        .placeholder(R.drawable.img_loading)
                        .error(R.drawable.img_not_found)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
                Text(
                    modifier = Modifier,
                    text = medicineInfo.medicineName,
                    style = PillinTimeTheme.typography.headline5Bold,
                    color = Gray90,
                )
                Spacer(modifier = Modifier.height(14.dp))
                Row {
                    Text(
                        text = "품목기준코드",
                        color = Gray40,
                        style = PillinTimeTheme.typography.body2Medium,
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = medicineInfo.medicineCode,
                        color = Gray70,
                        style = PillinTimeTheme.typography.body2Medium,
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                MedicineDetailItem(title = "효능", value = medicineInfo.medicineEffect)
                MedicineDetailItem(title = "복용 방법", value = medicineInfo.useMethod)
                MedicineDetailItem(title = "복용 전 알아두세요!", value = medicineInfo.useWarning)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AlertDialogPadding, 0.dp, AlertDialogPadding, AlertDialogPadding),
            ) {
                CustomButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(SmallSize),
                    enabled = true,
                    filled = ButtonColor.BLANK,
                    size = ButtonSize.MEDIUM,
                    text = "이전으로",
                    onClick = onDismiss
                )
                Spacer(modifier = Modifier.width(14.dp))
                CustomButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(SmallSize),
                    enabled = true,
                    filled = ButtonColor.FILLED,
                    size = ButtonSize.MEDIUM,
                    text = "선택하기",
                    onClick = {
                        onConfirm()
                    }
                )
            }
        }
    }
}

@Composable
fun MedicineDetailItem(
    title: String,
    value: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {
        Text(
            text = title,
            color = Gray40,
            style = PillinTimeTheme.typography.body2Medium
        )
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = value,
            color = Gray70,
            style = PillinTimeTheme.typography.body2Medium
        )
    }
}