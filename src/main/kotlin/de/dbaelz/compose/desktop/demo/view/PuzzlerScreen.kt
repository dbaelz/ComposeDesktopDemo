package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@ExperimentalFoundationApi
@Composable
fun PuzzlerScreen(onBackNavigation: () -> Unit) {
    MenuColumn(
        onBackNavigation, listOf(
            {
                Text("ModifierBorderPuzzler", style = MaterialTheme.typography.h3)
                Spacer(Modifier.height(8.dp))
                ModifierBorderPuzzler()
            },
            {
                Text("ModifierClickPuzzler", style = MaterialTheme.typography.h3)
                Spacer(Modifier.height(8.dp))
                ModifierClickPuzzler()
            },
        )
    )
}


@ExperimentalFoundationApi
@Composable
private fun ModifierBorderPuzzler(borderWidth: Dp = 12.dp) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .border(borderWidth, Color.Gray)
            .border(borderWidth, Color.Cyan)

            .padding(borderWidth)

            .border(borderWidth, Color.Blue)
            .border(borderWidth, Color.Magenta)

            .padding(borderWidth)

            .border(borderWidth, Color.Yellow)
            .border(borderWidth, Color.Black)

            .padding(borderWidth)
    )
}

@ExperimentalFoundationApi
@Composable
private fun ModifierClickPuzzler() {
    ClickableText(Modifier
        .clickable { println("ModifierClickPuzzler: clickable") }
        .combinedClickable(onClick = { println("ModifierClickPuzzler: combinedClickable onClick") })
    )
}

@ExperimentalFoundationApi
@Composable
private fun ClickableText(modifier: Modifier = Modifier) {
    Text(text = "Click me!",
        textAlign = TextAlign.Center,
        modifier = modifier
            .width(200.dp)
            .height(32.dp)
            .border(4.dp, Color.LightGray)
            .clickable { println("ClickableText: clickable") }
            .combinedClickable(onClick = { println("ClickableText: combinedClickable onClick") })
    )
}