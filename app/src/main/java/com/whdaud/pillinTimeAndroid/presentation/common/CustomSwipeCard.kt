package com.whdaud.pillinTimeAndroid.presentation.common

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whdaud.pillinTimeAndroid.R
import com.whdaud.pillinTimeAndroid.data.remote.dto.RelationDTO
import com.whdaud.pillinTimeAndroid.presentation.Dimens.BasicPadding
import com.whdaud.pillinTimeAndroid.presentation.main.MainViewModel
import com.whdaud.pillinTimeAndroid.presentation.mypage.editinfo.EditInfoBeforeItem
import com.whdaud.pillinTimeAndroid.ui.theme.Error60
import com.whdaud.pillinTimeAndroid.ui.theme.Gray10
import com.whdaud.pillinTimeAndroid.ui.theme.Gray70
import com.whdaud.pillinTimeAndroid.ui.theme.Gray90
import com.whdaud.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.whdaud.pillinTimeAndroid.ui.theme.White
import com.whdaud.pillinTimeAndroid.util.fadeInEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


private enum class HorizontalDragValue { Settled, EndToStart }

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CustomSwipeCard(
    isManager: Boolean? = true,
    mainViewModel: MainViewModel = hiltViewModel(),
    onRemove: (RelationDTO) -> Unit = {},
    onDisconnect: (Int) -> Unit = {},
) {
    val relationList by mainViewModel.relationInfoList.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var selectedRelation by remember { mutableStateOf<RelationDTO?>(null) }
    val showCabinetDialog = remember { mutableStateOf(false) }
    val showRelationDialog = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope { Dispatchers.Main }

    LaunchedEffect(relationList) {
        Log.e("CustomSwipeCard", "relationList updated: $relationList")
    }

    if (showBottomSheet && isManager == true) {
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
                modifier = Modifier.padding(BasicPadding)
            ) {
                selectedRelation?.let { relation ->
                    Text(
                        text = "기본 정보",
                        color = Gray90,
                        style = PillinTimeTheme.typography.headline5Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    EditInfoBeforeItem("성명", relation.memberName)
                    EditInfoBeforeItem("휴대폰 번호", relation.memberPhone)
                    EditInfoBeforeItem("주민등록번호", "${relation.memberSsn}●●●●●●")
                    Spacer(modifier = Modifier.weight(1f))
                    if(relation.cabinetId != 0) {
                        CustomButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            enabled = true,
                            filled = ButtonColor.FILLED,
                            size = ButtonSize.MEDIUM,
                            text = "약통 해제",
                            onClick = {
                                showCabinetDialog.value = true
                            }
                        )
                    }
                    CustomButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = BasicPadding),
                        enabled = true,
                        filled = ButtonColor.FILLED,
                        size = ButtonSize.MEDIUM,
                        text = "보호관계 삭제",
                        onClick = {
                            showRelationDialog.value = true
                        }
                    )
                    if(showCabinetDialog.value) {
                        CustomAlertDialog(
                            title = "${relation.memberName}님의 약통 연결을 해제하시겠습니까?",
                            description = "약통의 연결을 해제하시게 되면 더 이상 복용 스케쥴을\n추가하실 수 없게 됩니다.",
                            confirmText = "해제하기",
                            dismissText = "취소하기",
                            onConfirm = {
                                onDisconnect(relation.cabinetId)
                                showCabinetDialog.value = false
                                showBottomSheet = false
                            },
                            onDismiss = { showCabinetDialog.value = false }
                        )
                    }
                }
            }
        }
    }
    LazyColumn(
        modifier = Modifier.fadeInEffect()
    ) {
        items(items = relationList) { relations ->
            var boxSize by remember { mutableFloatStateOf(0F) }
            val anchors = DraggableAnchors {
                HorizontalDragValue.Settled at 0f
                HorizontalDragValue.EndToStart at -boxSize / 5
            }
            val state = remember {
                AnchoredDraggableState(
                    initialValue = HorizontalDragValue.Settled,
                    positionalThreshold = { distance -> distance * 0.3f },
                    velocityThreshold = { 0.3f },
                    animationSpec = tween(),
                )
            }
            SideEffect { state.updateAnchors(anchors) }

            val iconsBackgroundColor by animateColorAsState(
                when (state.targetValue) {
                    HorizontalDragValue.Settled -> Gray70
                    HorizontalDragValue.EndToStart -> Error60
                }, label = "change color"
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItemPlacement()
            ) {
                if(showRelationDialog.value) {
                    CustomAlertDialog(
                        title = "${selectedRelation?.memberName}님과의 연결을 해제하시겠습니까?",
                        description = "보호관계 연결을 해제하시게 되면 더 이상 해당 사용자를\n관리하실 수 없게 됩니다.",
                        confirmText = "해제하기",
                        dismissText = "취소하기",
                        onConfirm = {
                            selectedRelation?.let { onRemove(it) }
                            showRelationDialog.value = false
                            showBottomSheet = false
                        },
                        onDismiss = { showRelationDialog.value = false }
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .height(70.dp)
                        .background(iconsBackgroundColor)
                        .align(Alignment.CenterEnd)
                        .clickable(onClick = {
                            showRelationDialog.value = true
                            selectedRelation = relations
                        }),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "삭제",
                        color = White,
                        style = PillinTimeTheme.typography.body2Regular
                    )
                }
                Box(modifier = Modifier
                    .graphicsLayer { boxSize = size.width }
                    .offset {
                        IntOffset(
                            x = state
                                .requireOffset()
                                .roundToInt(), y = 0
                        )
                    }
                    .fillMaxWidth()
                    .height(70.dp)
                    .anchoredDraggable(state, Orientation.Horizontal)
                    .background(White)
                    .padding(start = 32.dp)
                    .clickable(
                        onClick = {
                            if (state.currentValue == HorizontalDragValue.Settled) {
                                selectedRelation = relations
                                showBottomSheet = true
                            } else {
                                scope.launch {
                                    state.animateTo(HorizontalDragValue.Settled)
                                }
                            }
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterStart),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = relations.memberName,
                                color = Gray90,
                                style = PillinTimeTheme.typography.headline5Bold
                            )
                            Text(
                                text = relations.memberPhone,
                                color = Gray70,
                                style = PillinTimeTheme.typography.body2Medium
                            )
                        }
                        val connectivityIcon = if (isManager == true) {
                            if (relations.cabinetId != 0) R.drawable.ic_connected else R.drawable.ic_disconnected
                        } else {
                            R.drawable.ic_manager
                        }
                        Icon(
                            modifier = Modifier.padding(end = 40.dp),
                            painter = painterResource(id = connectivityIcon),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                    HorizontalDivider(color = Gray10)
                }
            }
        }
    }
}