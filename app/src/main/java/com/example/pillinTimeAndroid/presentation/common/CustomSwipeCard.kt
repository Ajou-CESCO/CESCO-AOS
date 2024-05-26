package com.example.pillinTimeAndroid.presentation.common

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import com.example.pillinTimeAndroid.R
import com.example.pillinTimeAndroid.data.remote.dto.RelationDTO
import com.example.pillinTimeAndroid.ui.theme.Error60
import com.example.pillinTimeAndroid.ui.theme.Gray10
import com.example.pillinTimeAndroid.ui.theme.Gray70
import com.example.pillinTimeAndroid.ui.theme.Gray90
import com.example.pillinTimeAndroid.ui.theme.PillinTimeTheme
import com.example.pillinTimeAndroid.ui.theme.White
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


private enum class HorizontalDragValue { Settled, EndToStart }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomSwipeCard(
    isManager: Boolean? = true,
    relationList: List<RelationDTO>,
    onRemove: (RelationDTO) -> Unit = {}
) {

    LazyColumn {
        items(
            items = relationList,
            key = { it }
        ) { relation ->
            var boxSize by remember { mutableFloatStateOf(0F) }
            val scope = rememberCoroutineScope()
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .height(70.dp)
                        .background(iconsBackgroundColor)
                        .align(Alignment.CenterEnd)
                        .clickable (
                            onClick = { onRemove(relation) },
//                            indication = null,
//                            interactionSource = remember { MutableInteractionSource() }
                        ),
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
                        onClick = { scope.launch { state.animateTo(HorizontalDragValue.Settled) } },
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
                                text = relation.memberName,
                                color = Gray90,
                                style = PillinTimeTheme.typography.headline5Bold
                            )
                            Text(
                                text = relation.memberPhone,
                                color = Gray70,
                                style = PillinTimeTheme.typography.body2Medium
                            )
                        }
                        val connectivityIcon = if (isManager == true) {
                            if (relation.cabinetId != 0) R.drawable.ic_connected else R.drawable.ic_disconnected
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