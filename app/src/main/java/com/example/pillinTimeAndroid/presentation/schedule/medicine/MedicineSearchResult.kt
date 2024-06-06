package com.example.pillinTimeAndroid.presentation.schedule.medicine

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.data.remote.dto.MedicineDTO
import com.example.pillinTimeAndroid.ui.theme.Gray10
import com.example.pillinTimeAndroid.ui.theme.Gray20
import com.example.pillinTimeAndroid.ui.theme.Gray80
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.Success60
import com.example.pillinTimeAndroid.ui.theme.Warning60
import com.example.pillinTimeAndroid.ui.theme.White
import com.example.pillinTimeAndroid.ui.theme.shapes

@Composable
fun MedicineSearchResult(
    modifier: Modifier = Modifier,
    medicineList: List<MedicineDTO>,
    selectedMedicine: MedicineDTO?,
    onMedicineClick: (MedicineDTO) -> Unit
) {
    var localSelectedMedicine by remember { mutableStateOf<MedicineDTO?>(null) }
    val showDialog = remember { mutableStateOf(false) }

    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(medicineList) { medicine ->
            MedicineItem(
                medicine = medicine,
                isSelected = selectedMedicine == medicine,
                onClick = {
                    showDialog.value = true
                    localSelectedMedicine = medicine
                }
            )
            HorizontalDivider(color = Gray10)
        }
    }
    if (showDialog.value) {
        localSelectedMedicine?.let {
            MedicineDetailDialog(
                medicineInfo = it,
                onConfirm = {
                    onMedicineClick(it)
                    showDialog.value = false
                },
                onDismiss = {
                    showDialog.value = false
                }
            )
        }
    }
}

@Composable
fun MedicineItem(
    medicine: MedicineDTO,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    val lazyListState = rememberLazyListState()
    val effects = medicine.medicineEffect.split(",").map { it.trim() }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
            onClick = onClick,
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        )
    ) {
        RadioButton(
            modifier = Modifier
                .clickable(
                    onClick = onClick,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
                .align(Alignment.CenterEnd),
            selected = isSelected,
            colors = RadioButtonDefaults.colors(
                selectedColor = Primary60,
                unselectedColor = Gray20
            ),
            onClick = null
        )
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth(.8f)
                .padding(vertical = 8.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = medicine.medicineName,
                color = Gray90,
                style = PillinTimeTheme.typography.headline5Bold
            )
            LazyRow(
                state = lazyListState,
            ) {
                item {
                    val (sideEffects, backgroundColor) =
                        if (medicine.medicineAdverse?.dosageCaution != null ||
                            medicine.medicineAdverse?.ageSpecificContraindication != null ||
                            medicine.medicineAdverse?.elderlyCaution != null ||
                            medicine.medicineAdverse?.administrationPeriodCaution != null ||
                            medicine.medicineAdverse?.pregnancyContraindication != null ||
                            medicine.medicineAdverse?.duplicateEfficacyGroup != null)
                            Pair("부작용 주의", Warning60) else Pair("부작용 안전", Success60)
                    MedicineEffectChip(effect = sideEffects, backgroundColor = backgroundColor, textColor = White)
                }
                items(effects) { effect ->
                    MedicineEffectChip(effect)
                }
            }
        }
    }
}

@Composable
fun MedicineEffectChip(
    effect: String,
    backgroundColor: Color = Gray10,
    textColor: Color = Gray80
) {
    Box(
        modifier = Modifier.padding(end = 6.dp)
    ) {
        Text(
            modifier = Modifier
                .background(backgroundColor, shape = shapes.extraSmall)
                .padding(horizontal = 8.dp, vertical = 3.dp),
            text = effect,
            color = textColor,
            style = PillinTimeTheme.typography.caption2Medium
        )
    }
}