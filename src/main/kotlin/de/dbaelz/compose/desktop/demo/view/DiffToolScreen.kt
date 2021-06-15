package de.dbaelz.compose.desktop.demo.view

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
import de.dbaelz.compose.desktop.demo.feature.DiffUtils


@Composable
fun DiffToolScreen(
    diffUtils: DiffUtils,
    localAppWindow: AppWindow,
    onBackNavigation: () -> Unit = {}
) {
    var fileLeft by remember { mutableStateOf(emptyList<String>()) }
    var fileRight by remember { mutableStateOf(emptyList<String>()) }

    Column(Modifier.fillMaxWidth()) {
        ToolBar(onBackNavigation) {
            diffUtils.delta(fileLeft, fileRight)
        }

        Divider(color = MaterialTheme.colors.primaryVariant, thickness = 2.dp)

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
fun ToolBar(onBackNavigation: () -> Unit, onDiffClicked: () -> Unit) {
    Row(Modifier.padding(8.dp)) {
        Button(
            onClick = onBackNavigation,
            modifier = Modifier.height(32.dp)
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "back"
            )
        }

        Spacer(Modifier.width(24.dp))

        Button(
            onClick = onDiffClicked,
            modifier = Modifier.height(32.dp)
        ) {
            Text("Delta")
        }

        Spacer(Modifier.width(4.dp))

        Button(
            onClick = {  },
            modifier = Modifier.height(32.dp)
        ) {
            Text("Patch")
        }
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
    FileSelector(localAppWindow, updateLeftFile, updateRightFile)

    Divider(color = MaterialTheme.colors.primaryVariant, thickness = 2.dp)

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
            color = MaterialTheme.colors.primaryVariant, thickness = 2.dp
        )

        DiffLines(fileRight, Modifier.weight(0.5f), scrollStateRight)
    }
}

@Composable
fun FileSelector(
    localAppWindow: AppWindow,
    updateLeftFile: (List<String>) -> Unit,
    updateRightFile: (List<String>) -> Unit
) {
    var fileLeftName by remember { mutableStateOf(SELECT_FILE_LABEL) }
    var fileRightName by remember { mutableStateOf(SELECT_FILE_LABEL) }

    Row(
        Modifier
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SelectFileButton(fileLeftName, Modifier.weight(0.5f)) {
            selectAndReadFile(localAppWindow)?.let {
                fileLeftName = it.first
                updateLeftFile(it.second)
            }

        }

        Spacer(Modifier.width(16.dp))

        SelectFileButton(fileRightName, Modifier.weight(0.5f)) {
            selectAndReadFile(localAppWindow)?.let {
                fileRightName = it.first
                updateRightFile(it.second)
            }
        }
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
            LineNumber((lastIndex + 1).toString(), Modifier.alpha(0f))
            LineNumber((index + 1).toString(), Modifier.align(Alignment.CenterEnd))
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

private const val SELECT_FILE_LABEL = "Select file"

private fun selectAndReadFile(localAppWindow: AppWindow): Pair<String, List<String>>? {
    val fileDialog = java.awt.FileDialog(localAppWindow.window)
    fileDialog.isVisible = true

    val selectedFile = fileDialog.files.firstOrNull()
    if (selectedFile != null) {
        return selectedFile.name to selectedFile.readLines()
    }

    return null
}