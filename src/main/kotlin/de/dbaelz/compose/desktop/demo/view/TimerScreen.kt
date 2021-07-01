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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.max

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
        onButtonClicked = {
            if (periodLeft <= 0) {
                periodLeft = initialTimerPeriod
                timerActive = true
            } else {
                timerActive = !timerActive
            }
        }
    )
}

@Composable
private fun CircleTimer(
    modifier: Modifier,
    colorActive: Color = MaterialTheme.colors.secondary,
    colorInactive: Color = MaterialTheme.colors.onSecondary,
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
                startAngle = 135f,
                sweepAngle = FULL_TIMER_ANGLE,
                size = Size(size.width, size.height),
                useCenter = false,
                style = Stroke(width = 16f, cap = StrokeCap.Round)
            )

            drawArc(
                color = colorActive,
                startAngle = 135f,
                sweepAngle = timerAngle,
                size = Size(size.width, size.height),
                useCenter = false,
                style = Stroke(width = 10f, cap = StrokeCap.Round)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(100.dp)
        ) {
            Text(
                text = timeLeftText,
                color = colorActive,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(colorInactive)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorInactive,
                    contentColor = colorActive
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
}

private const val FULL_TIMER_ANGLE = 270f