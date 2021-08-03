package de.dbaelz.compose.desktop.demo.view

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun CanvasScreen(onBackNavigation: () -> Unit) {
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
    val barProgress by transition.animateValue(
        0, BAR_ITEMS + 1, Int.VectorConverter,
        InfiniteRepeatableSpec(
            animation = tween(ANIMATION_DURATION_MILLIS),
            repeatMode = RepeatMode.Reverse
        )
    )

    Screen(
        { ScreenTopBar("Canvas Examples with Animation", onBackNavigation) },
        listOf {
            PulsingCircle(
                modifier = Modifier.size(600.dp),
                color = color,
                radius = radius,
                barProgress = barProgress
            )
        },
    )
}

@Composable
fun PulsingCircle(
    modifier: Modifier = Modifier,
    color: Color,
    radius: Float,
    barProgress: Int
) {
    val colorBarIndicator = colors.primary
    val colorBarIndicatorBackground = colors.secondary

    Canvas(modifier = modifier.fillMaxSize()) {
        (1..BAR_ITEMS).forEach { index ->
            drawRoundRect(
                color = if (index <= barProgress) colorBarIndicator else colorBarIndicatorBackground,
                topLeft = Offset((BAR_WIDTH * 3) + index * BAR_WIDTH * 1.5f, 20f),
                size = Size(BAR_WIDTH, 50f),
                cornerRadius = CornerRadius(5f)
            )
        }

        drawCircle(
            color = color,
            center = center.copy(y = center.y + 50f),
            radius = radius,
            style = Stroke(width = 20f)
        )


    }
}

private const val ANIMATION_DURATION_MILLIS = 1500
private const val BAR_ITEMS = 20
private const val BAR_WIDTH = 15f