package de.dbaelz.compose.desktop.demo.ui

import androidx.compose.desktop.AppWindow
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.dbaelz.compose.desktop.demo.feature.selectAndReadFile


@Composable
fun DiffToolScreen(localAppWindow: AppWindow, onBackNavigation: () -> Unit = {}) {
    var fileLeft by remember { mutableStateOf(emptyList<String>()) }
    var fileRight by remember { mutableStateOf(emptyList<String>()) }

    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(8.dp)) {
            Button(
                onClick = onBackNavigation,
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "back"
                )
            }

            /*
            Spacer(Modifier.width(16.dp))

            Button(
                onClick = {
                    differ(fileLeft, fileRight)
                }
            ) {
                Text("Diff files")
            }
            */
        }

        Divider(color = MaterialTheme.colors.primary, thickness = 2.dp)

        DiffView(localAppWindow,
            fileLeft,
            fileRight,
            {
                fileLeft = it
            }, {
                fileRight = it
            })
    }
}

@Composable
fun SelectFileButton(text: String, modifier: Modifier = Modifier, onClickAction: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onClickAction
    ) {
        Text(text)
    }
}

@Composable
fun DiffView(
    localAppWindow: AppWindow,
    fileLeft: List<String>,
    fileRight: List<String>,
    updateLeftFile: (List<String>) -> Unit,
    updateRightFile: (List<String>) -> Unit
) {
    Row(
        Modifier
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SelectFileButton("Select left file", Modifier.weight(0.5f)) {
            selectAndReadFile(localAppWindow)?.let {
                updateLeftFile(it)
            }

        }

        Spacer(Modifier.width(8.dp))

        SelectFileButton("Select right file", Modifier.weight(0.5f)) {
            selectAndReadFile(localAppWindow)?.let {
                updateRightFile(it)
            }
        }
    }

    Divider(color = MaterialTheme.colors.primary, thickness = 2.dp)

    // TODO: How to sync both? One state doesn't work on different files
    val scrollStateLeft = rememberScrollState(0)
    val scrollStateRight = rememberScrollState(0)
    Row(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        DiffLines(fileLeft, Modifier.weight(0.5f), scrollStateLeft)

        Divider(
            modifier = Modifier.fillMaxHeight().width(2.dp),
            color = MaterialTheme.colors.primary, thickness = 2.dp
        )

        DiffLines(fileRight, Modifier.weight(0.5f), scrollStateRight)
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

