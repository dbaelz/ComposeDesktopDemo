package de.dbaelz.compose.desktop.demo.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
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
            { Puzzler("TextAndLocalProviderPuzzler") { TextAndLocalProviderPuzzler() } },
        )
    )
}

@Preview
@ExperimentalFoundationApi
@Composable
private fun puzzlerPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        ModifierBorderPuzzler()

        Spacer(modifier = Modifier.height(8.dp))

        ModifierBorderSimplified()

        Spacer(modifier = Modifier.height(8.dp))

        ModifierClickPuzzler()

        Spacer(modifier = Modifier.height(8.dp))

        ModifierButtonClickPuzzler()

        Spacer(modifier = Modifier.height(8.dp))

        TextAndLocalProviderPuzzler()
    }
}


@Composable
private fun Puzzler(text: String, puzzler: @Composable () -> Unit) {
    Text(text, style = MaterialTheme.typography.h5)
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

@Composable
private fun TextAndLocalProviderPuzzler() {
    CompositionLocalProvider(
        LocalTextStyle provides TextStyle(color = Color.Magenta),
        LocalContentColor provides Color.Cyan
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides TextStyle(color = Color.Red),
            LocalContentColor provides Color.Blue
        ) {
            Text(text = "Hello Puzzlers!", fontSize = 24.sp)
        }
    }
}