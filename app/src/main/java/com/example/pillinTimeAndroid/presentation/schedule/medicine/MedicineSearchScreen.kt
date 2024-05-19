package com.example.pillinTimeAndroid.presentation.schedule.medicine

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.data.remote.dto.MedicineDTO
import com.example.pillinTimeAndroid.presentation.common.CustomTextField
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme

@Composable
fun MedicineSearchScreen(
    value: String,
    onValueChange: (String) -> Unit,
    medicineInfo: List<MedicineDTO>,
    searchStatus: Boolean,
    selectedMedicine: MedicineDTO,
    onMedicineClick: () -> Unit
) {
    CustomTextField(
        state = true,
        hint = "의약품명 검색",
        value = value,
        onValueChange = onValueChange,
        trailIcon = R.drawable.ic_search,
    )
    if (medicineInfo.isNotEmpty() && searchStatus) {
        MedicineSearchResult(
            medicineList = medicineInfo,
            selectedMedicine = selectedMedicine,
            onMedicineClick = {medicine ->
//                viewModel.selectMedicine(medicine)
            },
        )
    } else if(medicineInfo.isEmpty() && searchStatus) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.6f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_pill_not_found),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "검색어를 다시 확인해주세요",
                color = Gray90,
                style = PillinTimeTheme.typography.caption1Bold
            )
            Text(
                text = "검색 결과가 없습니다.",
                color = Gray90,
                style = PillinTimeTheme.typography.caption1Regular
            )
        }
    } else {
        MedicineSearchResult(
            medicineList = emptyList(),
            selectedMedicine = null,
            onMedicineClick = { },
        )
    }
}