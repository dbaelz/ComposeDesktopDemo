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

        Spacer(Modifier.height(64.dp))

        Timer(
            modifier = Modifier.size(200.dp).align(Alignment.CenterHorizontally),
            style = TimerStyle(
                colorActive = MaterialTheme.colors.secondary,
                colorInactive = MaterialTheme.colors.onSecondary,
                arcWithKnob = false
            ),
            remember { TimerState(10_000) }
        )

        Spacer(Modifier.height(64.dp))

        val timerState = remember { TimerState(7500) }
        val timerModifier = Modifier.size(200.dp).align(Alignment.CenterHorizontally)
        Timer(
            modifier = timerModifier,
            style = TimerStyle(
                colorActive = MaterialTheme.colors.primary,
                colorInactive = MaterialTheme.colors.onPrimary,
                arcWithKnob = true,
                arcWithBorder = false
            ),
            timerState = timerState,
            bar = {
                BarTimer(
                    timerModifier.fillMaxSize(0.5f),
                    MaterialTheme.colors.primary,
                    MaterialTheme.colors.onPrimary,
                    max(timerState.periodLeft / timerState.timerDuration.toFloat(), 0f)
                )
            },
        )
    }
}

@Composable
private fun Timer(
    modifier: Modifier,
    style: TimerStyle,
    timerState: TimerState,
    bar: @Composable (ColumnScope.() -> Unit)? = null
) {
    LaunchedEffect(timerState.periodLeft, timerState.timerActive) {
        if (timerState.timerActive && timerState.periodLeft > 0) {
            delay(100)
            timerState.periodLeft -= 100
        }
    }

    Column(modifier = modifier) {
        CircleTimer(
            modifier = modifier.fillMaxSize(0.5f),
            style = style,
            timerAngle = FULL_TIMER_ANGLE * max(
                timerState.periodLeft / timerState.timerDuration.toFloat(),
                0f
            ),
            timeLeftText = timerState.periodLeft.toString(),
            buttonText = when {
                timerState.periodLeft <= 0 -> "Restart"
                timerState.timerActive -> "Pause"
                timerState.periodLeft != timerState.timerDuration -> "Resume"
                else -> "Start"
            },
            onButtonClicked = {
                if (timerState.periodLeft <= 0) {
                    timerState.periodLeft = timerState.timerDuration
                    timerState.timerActive = true
                } else {
                    timerState.timerActive = !timerState.timerActive
                }
            }
        )

        if (bar != null) {
            bar()
        }
    }

}

@Composable
private fun CircleTimer(
    modifier: Modifier = Modifier,
    style: TimerStyle,
    timerAngle: Float,
    timeLeftText: String,
    buttonText: String,
    onButtonClicked: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Canvas(
            modifier = modifier
        ) {
            drawArc(
                color = style.colorInactive,
                startAngle = START_ANGLE,
                sweepAngle = FULL_TIMER_ANGLE,
                size = Size(size.width, size.height),
                useCenter = false,
                style = Stroke(width = 16f, cap = StrokeCap.Round)
            )

            drawArc(
                color = style.colorActive,
                startAngle = START_ANGLE,
                sweepAngle = timerAngle,
                size = Size(size.width, size.height),
                useCenter = false,
                style = Stroke(width = if (style.arcWithBorder) 10f else 17f, cap = StrokeCap.Round)
            )

            if (style.arcWithKnob) {
                val radius = size.width / 2
                val x = center.x + radius * cos((timerAngle + START_ANGLE) * PI / 180)
                val y = center.y + radius * sin((timerAngle + START_ANGLE) * PI / 180)

                drawCircle(
                    color = style.colorActive,
                    radius = 12f,
                    center = Offset(x.toFloat(), y.toFloat())
                )
            }
        }

        TimerButton(
            style.colorActive,
            style.colorInactive,
            timeLeftText,
            buttonText,
            onButtonClicked
        )
    }
}

@Composable
private fun BarTimer(
    modifier: Modifier = Modifier,
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
                size = Size(size.width, 20f)
            )

            drawRoundRect(
                color = colorActive,
                size = Size(fillPercentage * size.width, 20f)
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

class TimerState(val timerDuration: Int = 10_000) {
    var timerActive by mutableStateOf(false)
    var periodLeft by mutableStateOf(timerDuration)
}

data class TimerStyle(
    val colorActive: Color,
    val colorInactive: Color,
    val arcWithBorder: Boolean = true,
    val arcWithKnob: Boolean = false
)

private const val START_ANGLE = 135f
private const val FULL_TIMER_ANGLE = 270f