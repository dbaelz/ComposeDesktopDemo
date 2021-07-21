package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@ExperimentalFoundationApi
@Composable
fun PlaygroundScreen(onBackNavigation: () -> Unit) {
    MenuColumn(
        onBackNavigation, listOf(
            {
                ModifierPuzzler()
            },
        )
    )
}

@ExperimentalFoundationApi
@Composable
private fun ModifierPuzzler() {
    Text(text = "Click me!",
        modifier = Modifier
            .size(200.dp)
            .clickable {
                println("clickable")
            }
            .combinedClickable {
                println("combinedClickable onClick")
            }
            .border(12.dp, Color.Blue)
            .border(12.dp, Color.DarkGray)
            .padding(12.dp)
            .border(12.dp, Color.Red)
            .border(12.dp, Color.DarkGray)
            .padding(12.dp)
            .border(12.dp, Color.Green)
            .border(12.dp, Color.DarkGray)
            .padding(12.dp)
    )
}
