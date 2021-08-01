package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.mouse.MouseScrollUnit
import androidx.compose.ui.input.mouse.mouseScrollFilter
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import de.dbaelz.compose.desktop.demo.view.ScrollDirection.DOWN
import de.dbaelz.compose.desktop.demo.view.ScrollDirection.UP
import java.awt.event.MouseEvent

@ExperimentalFoundationApi
@Composable
fun MouseKeyboardScreen(onBackNavigation: () -> Unit) {
    var lastClickLabel by remember { mutableStateOf("") }
    var pointerOffset by remember { mutableStateOf("") }
    var hasAreaEntered by remember { mutableStateOf(false) }
    var mouseEventLabel by remember { mutableStateOf("") }
    var mouseScrollLabel by remember { mutableStateOf("") }

    var horizontalDragOffsetX by remember { mutableStateOf(0) }

    var dragOffsetX by remember { mutableStateOf(0) }
    var dragOffsetY by remember { mutableStateOf(0) }

    MenuColumn(
        onBackNavigation, listOf {
            MouseClickArea(
                withBorder = hasAreaEntered,
                onClick = { lastClickLabel = "Click" },
                onLongClick = { lastClickLabel = "Long Click" },
                onDoubleClick = { lastClickLabel = "Double Click" },
                onPointerChanged = { pointerOffset = it.toString() },
                onPointerEnterExit = { hasAreaEntered = it },
                onMouseEvent = { mouseEventLabel = it.button.toString() },
                onMouseScrollEvent = { mouseScrollLabel = it.name }
            )

            Spacer(Modifier.height(8.dp))

            Text(text = pointerOffset, style = MaterialTheme.typography.h5)

            Spacer(Modifier.height(8.dp))

            Row {
                Text(
                    text = lastClickLabel,
                    style = MaterialTheme.typography.h5
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = if (mouseEventLabel.isEmpty()) "" else "with button $mouseEventLabel",
                    style = MaterialTheme.typography.h5
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = if (mouseScrollLabel.isEmpty()) "" else "MouseScroll: $mouseScrollLabel",
                style = MaterialTheme.typography.h5
            )

            Spacer(Modifier.height(4.dp))

            Divider(modifier = Modifier.fillMaxWidth(), thickness = 4.dp)

            HorizontalDraggable(
                DragData(horizontalDragOffsetX, rememberDraggableState { delta ->
                    horizontalDragOffsetX += delta.toInt()
                })
            )

            Divider(modifier = Modifier.fillMaxWidth(), thickness = 4.dp)

            Draggable(dragOffsetX, dragOffsetY) { x, y ->
                dragOffsetX += x
                dragOffsetY += y
            }

            Spacer(Modifier.height(4.dp))

            Text(
                text = "Drag Offset: ($dragOffsetX, $dragOffsetY)",
                style = MaterialTheme.typography.h5
            )
        }
    )
}

@ExperimentalFoundationApi
@Composable
private fun MouseClickArea(
    modifier: Modifier = Modifier,
    withBorder: Boolean = false,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    onDoubleClick: () -> Unit = {},
    onPointerChanged: (Offset) -> Unit = {},
    onPointerEnterExit: (Boolean) -> Unit = {},
    onMouseEvent: (MouseEvent) -> Unit = {},
    onMouseScrollEvent: (ScrollDirection) -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(200.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colors.primary)
            .then(
                if (withBorder) Modifier.border(
                    4.dp,
                    MaterialTheme.colors.primaryVariant,
                    CircleShape
                ) else Modifier
            )
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
                onDoubleClick = onDoubleClick
            )
            .pointerMoveFilter(
                onMove = {
                    onPointerChanged(it)
                    false
                },
                onEnter = {
                    onPointerEnterExit(true)
                    false
                },
                onExit = {
                    onPointerEnterExit(false)
                    false
                }
            ).pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        awaitPointerEvent().mouseEvent?.let {
                            onMouseEvent(it)
                        }
                    }
                }
            }
            .mouseScrollFilter(
                onMouseScroll = { event, _ ->
                    onMouseScrollEvent(
                        when (val delta = event.delta) {
                            is MouseScrollUnit.Line -> if (delta.value < 0) UP else DOWN
                            is MouseScrollUnit.Page -> if (delta.value < 0) UP else DOWN
                        }
                    )
                    false
                }
            )
    ) {
        Text(text = "Click me")
    }
}

@Composable
private fun HorizontalDraggable(dragData: DragData) {
    Box(modifier = Modifier
        .padding(8.dp)
        .width(200.dp)
        .height(50.dp)
        .offset { IntOffset(dragData.offset, 0) }
        .border(4.dp, MaterialTheme.colors.primary)
        .draggable(
            orientation = Orientation.Horizontal,
            state = dragData.state
        ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Drag me horizontal!",
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun Draggable(offsetX: Int = 0, offsetY: Int = 0, onDragged: (Int, Int) -> Unit) {
    Box(modifier = Modifier
        .padding(8.dp)
        .width(200.dp)
        .height(50.dp)
        .offset { IntOffset(offsetX, offsetY) }
        .border(4.dp, MaterialTheme.colors.primary)
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consumeAllChanges()
                onDragged(dragAmount.x.toInt(), dragAmount.y.toInt())
            }
        },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Drag me anywhere!",
            textAlign = TextAlign.Center
        )
    }
}

private data class DragData(val offset: Int, val state: DraggableState)

private enum class ScrollDirection { UP, DOWN }
