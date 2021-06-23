package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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

        Canvas()
    }
}

@Composable
fun Canvas() {
    val color = colors.primary

    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawCircle(
            color = color,
            center = Offset(canvasWidth / 2, canvasHeight / 2),
            radius = size.minDimension / 4
        )
    }
}