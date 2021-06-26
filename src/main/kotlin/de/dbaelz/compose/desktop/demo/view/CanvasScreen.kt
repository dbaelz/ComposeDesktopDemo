package de.dbaelz.compose.desktop.demo.view

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun CanvasScreen(onBackPressed: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onBackPressed,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Back to menu")
        }

        Spacer(modifier = Modifier.height(8.dp))

        PulsingCircle()
    }
}

@Composable
fun PulsingCircle() {
    val transition = rememberInfiniteTransition()
    val color by transition.animateColor(
        colors.secondary,
        colors.primary,
        InfiniteRepeatableSpec(
            animation = tween(ANIMATION_DURATION_MILLIS),
            repeatMode = RepeatMode.Reverse
        )
    )
    val radius by transition.animateFloat(
        0f,
        200f,
        InfiniteRepeatableSpec(
            animation = tween(ANIMATION_DURATION_MILLIS),
            repeatMode = RepeatMode.Reverse
        )
    )
    val barIndicator by transition.animateValue(
        0, BAR_ITEMS + 1, Int.VectorConverter,
        InfiniteRepeatableSpec(
            animation = tween(ANIMATION_DURATION_MILLIS),
            repeatMode = RepeatMode.Reverse
        )
    )
    val colorBarIndicator = colors.primary
    val colorBarIndicatorBackground = colors.secondary

    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawCircle(
            color = color,
            center = Offset(canvasWidth / 2, canvasHeight / 2),
            radius = radius,
            style = Stroke(width = 20f)
        )

        (1..BAR_ITEMS).forEach { index ->
            drawRoundRect(
                color = if (index <= barIndicator) colorBarIndicator else colorBarIndicatorBackground,
                topLeft = Offset((BAR_WIDTH * 3) + index * BAR_WIDTH * 1.5f, 20f),
                size = Size(BAR_WIDTH, 50f),
                cornerRadius = CornerRadius(5f)
            )
        }
    }
}

const val ANIMATION_DURATION_MILLIS = 1500
const val BAR_ITEMS = 10
const val BAR_WIDTH = 20f