package de.dbaelz.compose.desktop.demo.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun PlaygroundScreen(onBackNavigation: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BackButton(onBackNavigation)

        TextInOut("Hello there!")
    }
}

@Composable
fun TextInOut(text: String) {
    val transition = rememberInfiniteTransition()

    val dropText by transition.animateValue(
        0, text.length, Int.VectorConverter,
        InfiniteRepeatableSpec(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse
        )
    )
    Text(
        text = text.dropLast(dropText),
        fontSize = 32.sp,
        color = MaterialTheme.colors.primary,
        modifier = Modifier.fillMaxWidth(0.5f)
    )
}