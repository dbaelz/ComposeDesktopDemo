package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp
import java.awt.event.MouseEvent

@ExperimentalFoundationApi
@Composable
fun MouseKeyboardScreen(onBackNavigation: () -> Unit) {
    var lastClickLabel by remember { mutableStateOf("") }
    var pointerOffset by remember { mutableStateOf("") }
    var hasAreaEntered by remember { mutableStateOf(false) }
    var mouseEventLabel by remember { mutableStateOf("") }

    MenuColumn(
        onBackNavigation, listOf {
            MouseClickArea(
                withBorder = hasAreaEntered,
                onClick = { lastClickLabel = "Click" },
                onLongClick = { lastClickLabel = "LongClick" },
                onDoubleClick = { lastClickLabel = "DoubleClick" },
                onPointerChanged = { pointerOffset = it.toString() },
                onPointerEnterExit = { hasAreaEntered = it },
                onMouseEvent = { mouseEventLabel = it.button.toString() }
            )

            Spacer(Modifier.height(8.dp))

            Text(text = lastClickLabel, style = MaterialTheme.typography.h4)

            Spacer(Modifier.height(8.dp))

            Text(text = pointerOffset, style = MaterialTheme.typography.h5)

            Spacer(Modifier.height(8.dp))

            Text(
                text = if (mouseEventLabel.isEmpty()) "" else "Button $mouseEventLabel",
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
    onMouseEvent: (MouseEvent) -> Unit = {}
) {
    Box(
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
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Click me")
    }
}
