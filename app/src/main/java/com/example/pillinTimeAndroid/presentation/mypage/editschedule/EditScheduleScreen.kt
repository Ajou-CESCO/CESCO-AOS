package com.example.pillinTimeAndroid.presentation.mypage.editschedule

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.example.pillinTimeAndroid.presentation.common.CustomAlertDialog
import com.example.pillinTimeAndroid.presentation.common.CustomToast
import com.example.pillinTimeAndroid.presentation.common.CustomTopBar
import com.example.pillinTimeAndroid.presentation.home.components.convertToAmPm
import com.example.pillinTimeAndroid.presentation.schedule.medicine.MedicineEffectChip
import com.example.pillinTimeAndroid.ui.theme.Gray10
import com.example.pillinTimeAndroid.ui.theme.Gray5
import com.example.pillinTimeAndroid.ui.theme.Gray50
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.Primary60
import com.example.pillinTimeAndroid.ui.theme.Success60
import com.example.pillinTimeAndroid.ui.theme.Warning60
import com.example.pillinTimeAndroid.ui.theme.White
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditScheduleScreen(
    navController: NavHostController,
    viewModel: EditScheduleViewModel = hiltViewModel()
) {
    val userDetails by viewModel.userDetails.collectAsState()
    val relationInfoList by viewModel.relationInfoList.collectAsState()
    val doseSchedule by viewModel.doseSchedule.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState(initial = "")
    val showDialog = remember { mutableStateOf(false) }
    var onConfirm by remember { mutableStateOf({}) }
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        pageCount = {
            if (userDetails?.isManager == true) {
                relationInfoList.size
            } else 1
        }
    )
    var selectedUserIndex by remember { mutableIntStateOf(0) }
    val memberId = if (userDetails?.isManager == true) {
        if (relationInfoList.isNotEmpty()) {
            relationInfoList[selectedUserIndex].memberId
        } else {
            null
        }
    } else {
        userDetails?.memberId
    }
    LaunchedEffect(pagerState.currentPage) { selectedUserIndex = pagerState.currentPage }
    LaunchedEffect(memberId) {
        if (memberId != null) {
            viewModel.getDoseSchedule(memberId)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray5)
    ) {
        CustomTopBar(
            onBackClicked = { navController.popBackStack() },
            showBackButton = true,
            title = "복약 일정 관리"
        )
        Spacer(modifier = Modifier.padding(bottom = 28.dp))
        if (userDetails?.isManager == true) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(White)
            ) {
                items(relationInfoList.size) { index ->
                    val isSelected = selectedUserIndex == index
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    selectedUserIndex = index
                                    scope.launch {
                                        pagerState.scrollToPage(index)
                                    }
                                },
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            )
                            .padding(top = 24.dp, start = 24.dp),
                    ) {

                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .background(Primary60)
                                    .padding(top = 8.dp) // Adding padding to ensure it's visible
                            )
                        }
                        Text(
                            text = relationInfoList[index].memberName,
                            color = if (isSelected) Gray90 else Gray50,
                            style = PillinTimeTheme.typography.body1Medium
                        )
                    }
                }
            }
        }
        // schedule column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(top = 12.dp),
            verticalArrangement = Arrangement.Top
        ) {
            val textStyle = PillinTimeTheme.typography.body1Medium.copy(color = Gray90)
            val scheduleNumber = buildAnnotatedString {
                append("등록된 약 ")
                withStyle(style = textStyle.toSpanStyle().copy(color = Primary60)) {
                    append("${doseSchedule.size}")
                }
            }
            Text(
                modifier = Modifier.padding(horizontal = BasicPadding),
                text = scheduleNumber,
                color = Gray70,
                style = PillinTimeTheme.typography.body1Medium
            )
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Top,
                state = pagerState
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White),
                ) {
                    if (doseSchedule.isNotEmpty()) {
                        items(doseSchedule) { schedule ->
                            Row(
                                modifier = Modifier
                                    .padding(vertical = 16.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(horizontal = BasicPadding)
                                        .weight(.9f)
                                ) {
                                    Text(
                                        modifier = Modifier.padding(bottom = 4.dp),
                                        text = schedule.medicineName,
                                        color = Gray90,
                                        style = PillinTimeTheme.typography.headline5Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    val weekdayNames = listOf("월", "화", "수", "목", "금", "토", "일")
                                    val dayNames =
                                        schedule.weekdayList.joinToString(", ") { weekday ->
                                            weekdayNames.getOrNull(weekday - 1) ?: "알 수 없음"
                                        }
                                    val timeNames =
                                        schedule.timeList.sorted().joinToString(", ") { time ->
                                            convertToAmPm(time.substring(0, 5))
                                        }
                                    val (sideEffects, backgroundColor) =
                                        if (schedule.medicineAdverse.dosageCaution != null ||
                                            schedule.medicineAdverse.ageSpecificContraindication != null ||
                                            schedule.medicineAdverse.elderlyCaution != null ||
                                            schedule.medicineAdverse.administrationPeriodCaution != null ||
                                            schedule.medicineAdverse.pregnancyContraindication != null ||
                                            schedule.medicineAdverse.duplicateEfficacyGroup != null)
                                            Pair("부작용 주의", Warning60) else Pair("부작용 안전", Success60)

                                    LazyRow {
                                        item { MedicineEffectChip(effect = sideEffects, backgroundColor = backgroundColor, textColor = White) }
                                        item { MedicineEffectChip(effect = timeNames) }
                                        item { MedicineEffectChip(effect = dayNames) }
                                    }
                                }
                                Icon(
                                    modifier = Modifier
                                        .padding(end = 12.dp)
                                        .clickable(
                                            onClick = {
                                                showDialog.value = true
                                                onConfirm =
                                                    {
                                                        viewModel.deleteDoseSchedule(
                                                            memberId!!,
                                                            schedule.medicineId,
                                                            schedule.cabinetIndex
                                                        )
                                                    }
                                            },
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        ),
                                    painter = painterResource(id = R.drawable.ic_delete),
                                    contentDescription = null,
                                    tint = Color.Unspecified)
                            }

                            HorizontalDivider(color = Gray10)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            // successfully deleted schedule
            if (toastMessage.isNotEmpty()) {
                CustomToast(
                    modifier = Modifier.padding(bottom = 12.dp),
                    text = toastMessage,
                    onDismiss = { }
                )
            }
            // dialog for deleting schedule
            if (showDialog.value) {
                CustomAlertDialog(
                    title = "선택한 일정을\n삭제하시겠습니까?",
                    description = "삭제하면 해당 일정에 대해 관리하지 못해요.",
                    confirmText = "삭제하기",
                    dismissText = "취소하기",
                    onConfirm = {
                        onConfirm()
                        showDialog.value = false
                    },
                    onDismiss = { showDialog.value = false })
            }
        }
    }
}