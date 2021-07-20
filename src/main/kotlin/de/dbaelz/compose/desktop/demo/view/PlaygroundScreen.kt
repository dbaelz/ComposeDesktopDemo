package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PlaygroundScreen(onBackNavigation: () -> Unit) {
    MenuColumn(
        onBackNavigation, listOf(
            {
                RowWithChildsMaxWidthText()
            },
        )
    )
}

@Composable
private fun RowWithChildsMaxWidthText() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Row(
            Modifier.width(IntrinsicSize.Max).fillMaxHeight()
        ) {
            Box(
                modifier = Modifier.fillMaxHeight().weight(1f)
                    .background(MaterialTheme.colors.primary)
            ) {
                Text(text = "Short text")
            }

            Spacer(Modifier.width(16.dp))

            Box(
                modifier = Modifier.fillMaxHeight().weight(1f)
                    .background(MaterialTheme.colors.secondary)
            ) {
                Text(text = "Very very very long text")
            }
        }
    }
}