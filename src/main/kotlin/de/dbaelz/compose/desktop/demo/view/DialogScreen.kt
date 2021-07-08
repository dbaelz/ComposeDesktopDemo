package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.v1.DialogProperties

@ExperimentalComposeUiApi
@Composable
fun DialogScreen(onBackNavigation: () -> Unit) {
    var dialog by remember { mutableStateOf(DialogType.NONE) }

    val buttons = listOf<@Composable () -> Unit>(
        {
            Button("Alert Dialog") {
                dialog = DialogType.ALERT
            }
        },
        {
            Button("Dialog Window") {
                dialog = DialogType.WINDOW
            }
        },
        {
            Button("POPUP") {
                dialog = DialogType.POPUP
            }
        },
    )

    MenuColumn(onBackNavigation, buttons)

    val dismissDialog = { dialog = DialogType.NONE }
    when (dialog) {
        DialogType.NONE -> {
        }
        DialogType.ALERT -> AlertDialog(dismissDialog)
        DialogType.WINDOW -> DialogWindow(dismissDialog)
        DialogType.POPUP -> Popup()
    }
}

@Composable
private fun Button(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text)
    }
}

@Composable
private fun AlertDialog(onClickAndDismiss: () -> Unit) {
    AlertDialog(
        modifier = Modifier.border(width = 2.dp, MaterialTheme.colors.primary),
        properties = DialogProperties(
            title = "Info",
            size = IntSize(300, 100),
            undecorated = true
        ),
        text = {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Another useful dialog",
                )
            }

        },
        confirmButton = {
            Box(modifier = Modifier.fillMaxSize()) {
                Button(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = onClickAndDismiss
                ) {
                    Text("OK")
                }
            }
        },
        onDismissRequest = onClickAndDismiss
    )
}

@ExperimentalComposeUiApi
@Composable
private fun DialogWindow(onClickAndDismiss: () -> Unit) {
    Dialog(
        title = "Dialog Window",
        onCloseRequest = {
            onClickAndDismiss()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "This is dialog window",
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )
            Button("Close", onClickAndDismiss)
        }
    }
}

@Composable
private fun Popup() {
    Popup(alignment = Alignment.BottomCenter) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .background(MaterialTheme.colors.primary),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Popup",
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
            )
        }
    }
}

private enum class DialogType {
    NONE,
    ALERT,
    WINDOW,
    POPUP,
}