package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.max

@Composable
fun TimerScreen(onBackNavigation: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onBackNavigation,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Back to menu")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Timer(Modifier.size(200.dp).align(Alignment.CenterHorizontally), 10_000)
    }
}

@Composable
fun Timer(
    modifier: Modifier,
    initialTimerPeriod: Int
) {
    val colorActive = MaterialTheme.colors.secondary
    val colorInactive = MaterialTheme.colors.onSecondary

    var timerActive by remember { mutableStateOf(false) }
    var periodLeft by remember { mutableStateOf(initialTimerPeriod) }

    LaunchedEffect(periodLeft, timerActive) {
        if (timerActive && periodLeft > 0) {
            delay(100)
            periodLeft -= 100
        }
    }

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
                style = Stroke(width = 10f, cap = StrokeCap.Round)
            )

            drawArc(
                color = colorActive,
                startAngle = 135f,
                sweepAngle = FULL_TIMER_ANGLE * max(periodLeft / initialTimerPeriod.toFloat(), 0f),
                size = Size(size.width, size.height),
                useCenter = false,
                style = Stroke(width = 10f, cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = if (periodLeft > 0) periodLeft.toString() else "",
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                if (periodLeft <= 0) {
                    periodLeft = initialTimerPeriod
                    timerActive = true
                } else {
                    timerActive = !timerActive
                }
            }) {
                Text(
                    text = when {
                        periodLeft <= 0 -> "Restart"
                        timerActive -> "Pause"
                        periodLeft != initialTimerPeriod -> "Resume"
                        else -> "Start"
                    }
                )
            }
        }
    }
}

const val FULL_TIMER_ANGLE = 270f