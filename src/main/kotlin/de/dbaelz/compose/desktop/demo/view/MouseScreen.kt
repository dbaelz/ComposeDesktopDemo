package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.mouse.MouseScrollUnit
import androidx.compose.ui.input.mouse.mouseScrollFilter
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import de.dbaelz.compose.desktop.demo.view.ScrollDirection.DOWN
import de.dbaelz.compose.desktop.demo.view.ScrollDirection.UP
import java.awt.event.MouseEvent

@ExperimentalDesktopApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun MouseScreen(onBackNavigation: () -> Unit) {
    var lastClickLabel by remember { mutableStateOf("") }
    var pointerOffset by remember { mutableStateOf("") }
    var hasAreaEntered by remember { mutableStateOf(false) }
    var mouseEventLabel by remember { mutableStateOf("") }
    var mouseScrollLabel by remember { mutableStateOf("") }

    var horizontalDragOffsetX by remember { mutableStateOf(0) }
    var horizontalIsDragged by remember { mutableStateOf(false) }

    var dragOffsetX by remember { mutableStateOf(0) }
    var dragOffsetY by remember { mutableStateOf(0) }
    var isDragged by remember { mutableStateOf(false) }

    CompositionLocalProvider(
        LocalTextStyle provides MaterialTheme.typography.h5,
        LocalContentColor provides MaterialTheme.colors.onBackground
    ) {
        Screen(
            { ScreenTopBar("Mouse Input", onBackNavigation) },
            listOf {
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

                Text(text = pointerOffset)

                Spacer(Modifier.height(8.dp))

                Row {
                    Text(lastClickLabel)

                    Spacer(Modifier.width(8.dp))

                    Text(if (mouseEventLabel.isEmpty()) "" else "with button $mouseEventLabel")
                }

                Spacer(Modifier.height(8.dp))

                Text(if (mouseScrollLabel.isEmpty()) "" else "MouseScroll: $mouseScrollLabel")

                Spacer(Modifier.height(4.dp))

                Divider(modifier = Modifier.fillMaxWidth(), thickness = 4.dp)

                HorizontalDraggable(
                    dragData = DragData(horizontalDragOffsetX, rememberDraggableState { delta ->
                        horizontalDragOffsetX += delta.toInt()
                    }),
                    borderColor = if (horizontalIsDragged) MaterialTheme.colors.secondary else MaterialTheme.colors.primary,
                    borderShape = if (horizontalIsDragged) RoundedCornerShape(16.dp) else RectangleShape,
                    onDragStart = { horizontalIsDragged = true },
                    onDragEnd = { horizontalIsDragged = false },
                )

                RowDivider()

                Text("Drag Offset: ($dragOffsetX, $dragOffsetY)")

                Spacer(Modifier.height(4.dp))

                Draggable(
                    offsetX = dragOffsetX,
                    offsetY = dragOffsetY,
                    borderColor = if (isDragged) MaterialTheme.colors.secondary else MaterialTheme.colors.primary,
                    borderShape = if (isDragged) RoundedCornerShape(16.dp) else RectangleShape,
                    onDragStart = { isDragged = true },
                    onDragEnd = { isDragged = false },
                    onDrag = { x, y ->
                        dragOffsetX += x
                        dragOffsetY += y
                    }
                )

                RowDivider()

                Text(
                    text = "Pointer Icon example. Hover over the text",
                    modifier = Modifier.pointerIcon(PointerIcon.Hand)
                )

                RowDivider()

                MouseClickable()
            }
        )
    }
}

@Composable
private fun RowDivider() {
    Divider(modifier = Modifier.fillMaxWidth(), thickness = 4.dp)

    Spacer(Modifier.height(8.dp))
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
        Text(
            text = "Click and Scroll",
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
private fun HorizontalDraggable(
    dragData: DragData,
    borderColor: Color = MaterialTheme.colors.primary,
    borderShape: Shape = RectangleShape,
    onDragStart: () -> Unit = {},
    onDragEnd: () -> Unit = {}
) {
    Box(modifier = Modifier
        .padding(8.dp)
        .width(200.dp)
        .height(50.dp)
        .offset { IntOffset(dragData.offset, 0) }
        .border(4.dp, borderColor, borderShape)
        .draggable(
            orientation = Orientation.Horizontal,
            state = dragData.state,
            onDragStarted = { onDragStart() },
            onDragStopped = { onDragEnd() },
        ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Drag me horizontal!",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
private fun Draggable(
    offsetX: Int = 0,
    offsetY: Int = 0,
    borderColor: Color = MaterialTheme.colors.primary,
    borderShape: Shape = RectangleShape,
    onDragStart: () -> Unit = {},
    onDragEnd: () -> Unit = {},
    onDrag: (Int, Int) -> Unit
) {
    Box(modifier = Modifier
        .padding(8.dp)
        .width(200.dp)
        .height(50.dp)
        .offset { IntOffset(offsetX, offsetY) }
        .border(4.dp, borderColor, borderShape)
        .pointerInput(Unit) {
            detectDragGestures(
                onDragStart = { onDragStart() },
                onDragEnd = onDragEnd,
                onDrag = { change, dragAmount ->
                    change.consumeAllChanges()
                    onDrag(dragAmount.x.toInt(), dragAmount.y.toInt())
                })
        },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Drag me anywhere!",
            style = MaterialTheme.typography.body1
        )
    }
}

@ExperimentalDesktopApi
@Composable
private fun MouseClickable() {
    val baseText = "Mouse Clickable: "
    var mouseButtonText by remember { mutableStateOf("No button clicked") }
    var keyboardModifierText by remember { mutableStateOf("") }

    Text(
        text = "$baseText $mouseButtonText ${
            if (keyboardModifierText.isEmpty()) "without modifier" else "with modifier $keyboardModifierText"
        }",
        modifier = Modifier.mouseClickable {
            mouseButtonText = when {
                buttons.isPrimaryPressed -> "Button 1"
                buttons.isSecondaryPressed -> "Button 2"
                buttons.isTertiaryPressed -> "Button 3"
                else -> ""
            }
            keyboardModifierText = when {
                keyboardModifiers.isShiftPressed -> "SHIFT"
                keyboardModifiers.isAltPressed -> "ALT"
                keyboardModifiers.isCtrlPressed -> "CTRL"
                else -> ""
            }
        }
    )
}

private data class DragData(val offset: Int, val state: DraggableState)

private enum class ScrollDirection { UP, DOWN }
