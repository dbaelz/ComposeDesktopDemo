package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalFoundationApi
@Composable
fun PuzzlerScreen(onBackNavigation: () -> Unit) {
    Screen(
        { ScreenTopBar("Puzzler", onBackNavigation) },
        listOf(
            { Puzzler("ModifierBorderPuzzler") { ModifierBorderPuzzler() } },
            { Puzzler("ModifierBorderSimplified") { ModifierBorderSimplified() } },
            { Puzzler("ModifierClickPuzzler") { ModifierClickPuzzler() } },
            { Puzzler("ModifierButtonClickPuzzler") { ModifierButtonClickPuzzler() } },
        )
    )
}

@Composable
private fun Puzzler(text: String, puzzler: @Composable () -> Unit) {
    Text(text, style = MaterialTheme.typography.h3)
    Spacer(Modifier.height(8.dp))
    puzzler()
}

@Composable
private fun ModifierBorderSimplified(borderWidth: Dp = 12.dp) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .border(borderWidth, Color.Gray)
            .offset(4.dp, 4.dp)
            .border(borderWidth, Color.Cyan)
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
        .clickable { println("One") }
        .combinedClickable(onClick = { println("Two") })
    )
}

@ExperimentalFoundationApi
@Composable
private fun ClickableText(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .size(100.dp)
        .background(Color.Cyan)
        .clickable { println("Three") }
        .combinedClickable(onClick = { println("Four") })
    ) {
        Text(text = "Click me!",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = modifier
                .fillMaxSize()
                .clickable { println("Five") }
                .combinedClickable(onClick = { println("Six") })
        )
    }
}

@Composable
private fun ModifierButtonClickPuzzler() {
    Button(onClick = { println("One") },
        modifier = Modifier
            .size(100.dp)
            .background(Color.Cyan)
            .clickable { println("Two") }) {
        Text("Click me!")
    }
}