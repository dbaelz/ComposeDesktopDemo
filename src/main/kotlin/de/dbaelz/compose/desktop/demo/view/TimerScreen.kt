package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

@Composable
fun TimerScreen(onBackNavigation: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        BackButton(
            onBackNavigation,
            backgroundColor = MaterialTheme.colors.secondary
        )

        Timer(Modifier.size(280.dp).align(Alignment.CenterHorizontally), 10_000)
    }
}

@Composable
private fun Timer(
    modifier: Modifier,
    initialTimerPeriod: Int,
    colorActive: Color = MaterialTheme.colors.secondary,
    colorInactive: Color = MaterialTheme.colors.onSecondary,
) {
    var timerActive by remember { mutableStateOf(false) }
    var periodLeft by remember { mutableStateOf(initialTimerPeriod) }

    LaunchedEffect(periodLeft, timerActive) {
        if (timerActive && periodLeft > 0) {
            delay(100)
            periodLeft -= 100
        }
    }

    CircleTimer(
        modifier = modifier,
        colorActive = colorActive,
        colorInactive = colorInactive,
        timerAngle = FULL_TIMER_ANGLE * max(periodLeft / initialTimerPeriod.toFloat(), 0f),
        timeLeftText = periodLeft.toString(),
        buttonText = when {
            periodLeft <= 0 -> "Restart"
            timerActive -> "Pause"
            periodLeft != initialTimerPeriod -> "Resume"
            else -> "Start"
        },
        withKnob = true,
        onButtonClicked = {
            if (periodLeft <= 0) {
                periodLeft = initialTimerPeriod
                timerActive = true
            } else {
                timerActive = !timerActive
            }
        }
    )

    val timerBar = max(periodLeft / initialTimerPeriod.toFloat(), 0f)
    BarTimer(modifier, colorActive, colorInactive, timerBar)
}

@Composable
private fun CircleTimer(
    modifier: Modifier,
    colorActive: Color = MaterialTheme.colors.secondary,
    colorInactive: Color = MaterialTheme.colors.onSecondary,
    arcWithBorder: Boolean = true,
    withKnob: Boolean = false,
    timerAngle: Float,
    timeLeftText: String,
    buttonText: String,
    onButtonClicked: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Canvas(
            modifier = modifier
        ) {
            drawArc(
                color = colorInactive,
                startAngle = START_ANGLE,
                sweepAngle = FULL_TIMER_ANGLE,
                size = Size(size.width, size.height),
                useCenter = false,
                style = Stroke(width = 16f, cap = StrokeCap.Round)
            )

            val strokeWidth = if (arcWithBorder) 10f else 17f
            drawArc(
                color = colorActive,
                startAngle = START_ANGLE,
                sweepAngle = timerAngle,
                size = Size(size.width, size.height),
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            if (withKnob) {
                val radius = size.width / 2
                val x = center.x + radius * cos((timerAngle + START_ANGLE) * PI / 180)
                val y = center.y + radius * sin((timerAngle + START_ANGLE) * PI / 180)

                drawCircle(
                    color = colorActive,
                    radius = 12f,
                    center = Offset(x.toFloat(), y.toFloat())
                )
            }
        }

        TimerButton(colorActive, colorInactive, timeLeftText, buttonText, onButtonClicked)
    }
}

@Composable
private fun BarTimer(
    modifier: Modifier,
    colorActive: Color = MaterialTheme.colors.secondary,
    colorInactive: Color = MaterialTheme.colors.onSecondary,
    fillPercentage: Float
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Canvas(
            modifier = modifier
        ) {
            drawRoundRect(
                color = colorInactive,
                size = Size(280f, 40f)
            )

            drawRoundRect(
                color = colorActive,
                size = Size(fillPercentage * 280f, 40f)
            )
        }
    }
}

@Composable
private fun TimerButton(
    textColor: Color = MaterialTheme.colors.secondary,
    backgroundColor: Color = MaterialTheme.colors.onSecondary,
    timeLeftText: String,
    buttonText: String,
    onButtonClicked: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(100.dp)
    ) {
        Text(
            text = timeLeftText,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(backgroundColor)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = backgroundColor,
                contentColor = textColor
            ),
            modifier = Modifier.fillMaxWidth(),
            onClick = onButtonClicked
        ) {
            Text(
                text = buttonText
            )
        }
    }
}

private const val START_ANGLE = 135f
private const val FULL_TIMER_ANGLE = 270f