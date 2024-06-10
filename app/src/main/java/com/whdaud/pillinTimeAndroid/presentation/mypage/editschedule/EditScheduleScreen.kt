package com.whdaud.pillinTimeAndroid.presentation.mypage.editschedule

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.whdaud.pillinTimeAndroid.R
import com.whdaud.pillinTimeAndroid.data.remote.dto.ScheduleDTO
import com.whdaud.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.whdaud.pillinTimeAndroid.presentation.common.CustomAlertDialog
import com.whdaud.pillinTimeAndroid.presentation.common.CustomTopBar
import com.whdaud.pillinTimeAndroid.presentation.home.components.convertToAmPm
import com.whdaud.pillinTimeAndroid.presentation.schedule.medicine.MedicineEffectChip
import com.whdaud.pillinTimeAndroid.ui.theme.Error60
import com.whdaud.pillinTimeAndroid.ui.theme.Gray10
import com.whdaud.pillinTimeAndroid.ui.theme.Gray20
import com.whdaud.pillinTimeAndroid.ui.theme.Gray40
import com.whdaud.pillinTimeAndroid.ui.theme.Gray5
import com.whdaud.pillinTimeAndroid.ui.theme.Gray50
import com.whdaud.pillinTimeAndroid.ui.theme.Gray70
import com.whdaud.pillinTimeAndroid.ui.theme.Gray90
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.whdaud.pillinTimeAndroid.ui.theme.Primary40
import com.whdaud.pillinTimeAndroid.ui.theme.Primary60
import com.whdaud.pillinTimeAndroid.ui.theme.Purple60
import com.whdaud.pillinTimeAndroid.ui.theme.Success60
import com.whdaud.pillinTimeAndroid.ui.theme.Warning60
import com.whdaud.pillinTimeAndroid.ui.theme.White
import com.whdaud.pillinTimeAndroid.ui.theme.shapes
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
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
    val snackbarHostState = remember { SnackbarHostState() }
    val snackMessage = remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var selectedSchedule by remember { mutableStateOf<ScheduleDTO?>(null) }


    LaunchedEffect(pagerState.currentPage) { selectedUserIndex = pagerState.currentPage }
    LaunchedEffect(memberId) {
        if (memberId != null) {
            viewModel.getDoseSchedule(memberId)
        }
    }
    if(showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = BasicPadding),
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            containerColor = White
        ) {
            Column(
                modifier = Modifier.padding(horizontal = BasicPadding)
            ) {
                val title = "복약 계획 수정"
                Text(
                    modifier = Modifier.padding(bottom = 12.dp),
                    text =title,
                    color = Gray90,
                    style = PillinTimeTheme.typography.logo2Extra
                )
                EditScheduleDetailScreen(
                    schedule = selectedSchedule
                )
            }
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
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 4.dp),
                            text = relationInfoList[index].memberName,
                            color = if (isSelected) Gray90 else Gray50,
                            style = PillinTimeTheme.typography.body1Medium
                        )
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .height(2.dp)
                                    .clip(shapes.medium)
                                    .background(Primary60)
                                    .animateContentSize()
                            ) {
                                Text(text = "11111")
                            }
                        } else {
                            Box(modifier = Modifier
                                .height(2.dp)
                                .background(Color.Transparent))
                            {
                                Text(text = "11111")
                            }
                        }
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
            val indexColor = listOf(Error60, Warning60, Success60, Primary40, Purple60)
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
                if (doseSchedule.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize()
                            .background(White),
                    ) {
                        items(doseSchedule) { schedule ->
                            val cabinetColor =
                                if (schedule.cabinetIndex in 1..5) indexColor[schedule.cabinetIndex - 1]
                                else Gray20 // index out of range 방지
                            var expanded by remember { mutableStateOf(false) }

                            Row(
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .padding(horizontal = BasicPadding)
                                    .border(1.dp, Gray10, shapes.medium)
                                    .padding(vertical = 20.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = BasicPadding)
                                        .weight(.9f)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(bottom = 14.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Canvas(
                                            modifier = Modifier
                                                .weight(.1f)
                                                .size(20.dp)
                                                .padding(end = 4.dp)
                                        ) { drawCircle(color = cabinetColor, radius = size.minDimension / 2) }
                                        Text(
                                            modifier = Modifier.weight(1f),
                                            text = schedule.medicineName,
                                            color = Gray90,
                                            style = PillinTimeTheme.typography.headline5Bold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Column(
                                        ) {
                                            Icon(
                                                modifier = Modifier
                                                    .clickable(
                                                        onClick = { expanded = true },
                                                        indication = null,
                                                        interactionSource = remember { MutableInteractionSource() }
                                                    ),
                                                painter = painterResource(id = R.drawable.ic_more),
                                                contentDescription = null,
                                                tint = Gray40
                                            )
                                            DropdownMenu(
                                                modifier = Modifier
                                                    .background(color = White)
                                                    .border(1.dp, Gray5, shape = shapes.small)
                                                    .padding(8.dp),
                                                expanded = expanded,
                                                onDismissRequest = { expanded = false }
                                            ) {
                                                DropdownMenuItem(
                                                    onClick = {
                                                        showDialog.value = true
                                                        expanded = false
                                                        onConfirm = {
                                                            viewModel.deleteDoseSchedule(
                                                                memberId!!,
                                                                schedule.groupId
                                                            )
                                                        }
                                                    },
                                                    text = {
                                                        Text(
                                                            text = "삭제하기",
                                                            color = Gray70,
                                                            style = PillinTimeTheme.typography.body1Medium
                                                        )
                                                    },
                                                    trailingIcon = {
                                                        Icon(
                                                            imageVector = Icons.TwoTone.Delete,
                                                            contentDescription = null,
                                                            tint = Color.Unspecified
                                                        )
                                                    }
                                                )
                                                HorizontalDivider(color = Gray10)
                                                DropdownMenuItem(
                                                    onClick = {
                                                        expanded = false
                                                        selectedSchedule = schedule
                                                        showBottomSheet = true
                                                    },
                                                    text = {
                                                        Text(
                                                            text = "수정하기",
                                                            color = Gray70,
                                                            style = PillinTimeTheme.typography.body1Medium,
                                                        )
                                                    },
                                                    trailingIcon = {
                                                        Icon(
                                                            imageVector = Icons.TwoTone.Edit,
                                                            contentDescription = null,
                                                            tint = Gray70
                                                        )
                                                    }
                                                )
                                            }
                                        }
                                    }
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
                                            schedule.medicineAdverse.duplicateEfficacyGroup != null
                                        ) Pair("부작용 주의", Warning60) else Pair("부작용 안전", Success60)
                                    Row(
                                        modifier = Modifier.padding(bottom = 12.dp)
                                    ) {
                                        MedicineEffectChip(
                                            modifier = Modifier.padding(4.dp),
                                            effect = sideEffects,
                                            backgroundColor = backgroundColor,
                                            textColor = White
                                        )
                                    }
                                    LazyRow {
                                        item { MedicineEffectChip(modifier = Modifier.padding(4.dp), effect = dayNames) }
                                        item { MedicineEffectChip(modifier = Modifier.padding(4.dp), effect = timeNames) }
                                    }
                                }
                            }
                        }
                    }
                }else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = BasicPadding, vertical = 16.dp)
                            .clip(shapes.small)
                            .padding(44.dp)
                            .animateContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 2.dp),
                            text = "복약 계획이 없습니다.",
                            color = Gray90,
                            style = PillinTimeTheme.typography.body1Bold,
                        )
                        Text(
                            text = "복약 일정을 등록하고 알림을 받아보세요",
                            color = Gray90,
                            style = PillinTimeTheme.typography.caption1Medium,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            // successfully deleted schedule
            if (toastMessage.isNotEmpty()) {
//                CustomToast(
//                    modifier = Modifier.padding(bottom = 12.dp),
//                    text = toastMessage,
//                    onDismiss = { }
//                )
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