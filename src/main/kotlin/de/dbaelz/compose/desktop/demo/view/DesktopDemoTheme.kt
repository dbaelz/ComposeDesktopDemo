package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun DesktopDemoTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        Row(
            Modifier
                .border(
                    width = 16.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colors.secondary,
                            MaterialTheme.colors.primary
                        )
                    ),
                    shape = RectangleShape
                )
                .padding(16.dp)
                .fillMaxSize()
        ) {
            content()
        }
    }
}