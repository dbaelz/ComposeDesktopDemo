package de.dbaelz.compose.desktop.demo.difftool

import androidx.compose.desktop.AppWindow
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun DiffToolScreen(localAppWindow: AppWindow) {
    var fileLeft by remember { mutableStateOf(emptyList<String>()) }
    var fileRight by remember { mutableStateOf(emptyList<String>()) }

    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SelectFileButton("Select left file", Modifier.weight(0.5f)) {
                selectAndReadFile(localAppWindow)?.let {
                    fileLeft = it
                }

            }

            Spacer(Modifier.width(16.dp))

            SelectFileButton("Select right file", Modifier.weight(0.5f)) {
                selectAndReadFile(localAppWindow)?.let {
                    fileRight = it
                }
            }
        }

        SelectFileButton(
            "Compute Delta",
            Modifier.padding(PaddingValues(horizontal = 8.dp)).fillMaxWidth()
        ) {
            differ(fileLeft, fileRight)
        }

        Divider(color = MaterialTheme.colors.primary, thickness = 2.dp)

        DiffView(fileLeft, fileRight)
    }
}

@Composable
fun SelectFileButton(text: String, modifier: Modifier = Modifier, onClickAction: () -> Unit) {
    Button(
        modifier = modifier
            .padding(4.dp),
        onClick = onClickAction
    ) {
        Text(text)
    }
}

@Composable
fun DiffView(contentLeft: List<String> = emptyList(), contentRight: List<String> = emptyList()) {
    // TODO: How to sync both? One state doesn't work on different files
    val scrollStateLeft = rememberScrollState(0)
    val scrollStateRight = rememberScrollState(0)

    Row(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        DiffLines(contentLeft, Modifier.weight(0.5f), scrollStateLeft)

        Spacer(Modifier.width(16.dp))

        DiffLines(contentRight, Modifier.weight(0.5f), scrollStateRight)
    }
}

@Composable
fun DiffLines(content: List<String>, modifier: Modifier, scrollState: ScrollState) {
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        content.forEachIndexed { index, text ->
            DiffLine(index, content.lastIndex, text)
        }
    }

    if (content.isNotEmpty()) {
        VerticalScrollbar(
            modifier = Modifier.fillMaxHeight(),
            adapter = rememberScrollbarAdapter(scrollState)
        )
    }
}

@Composable
fun DiffLine(index: Int, lastIndex: Int, text: String) {
    Row {
        Box(modifier = Modifier.background(Color.LightGray)) {
            LineNumber(lastIndex.toString(), Modifier.alpha(0f))
            LineNumber(index.toString(), Modifier.align(Alignment.CenterEnd))
        }

        LineText(text)
    }
}

@Composable
fun LineNumber(number: String, modifier: Modifier) {
    Text(
        text = number,
        color = MaterialTheme.colors.primary,
        modifier = modifier
            .padding(4.dp)
    )
}

@Composable
fun LineText(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .padding(4.dp)
    )
}