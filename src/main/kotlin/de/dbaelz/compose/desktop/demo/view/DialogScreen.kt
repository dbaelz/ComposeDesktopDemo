package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.BoxWithTooltip
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun DialogScreen(onBackNavigation: () -> Unit) {
    var dialog by remember { mutableStateOf(DialogType.NONE) }
    
    Screen(
        navigation = { ScreenTopBar("Dialogs", onBackNavigation) },
        items = listOf<@Composable ColumnScope.() -> Unit>(
            {
                DialogButton("Alert Dialog") {
                    dialog = DialogType.ALERT
                }
            },
            {
                DialogButton("Dialog Window") {
                    dialog = DialogType.WINDOW
                }
            },
            {
                DialogButton("Popup") {
                    dialog = DialogType.POPUP
                }
            },
        )
    )

    val dismissDialog = { dialog = DialogType.NONE }
    val windowDialogState = rememberDialogState(size = WindowSize(640.dp, 480.dp))
    when (dialog) {
        DialogType.NONE -> {
        }
        DialogType.ALERT -> AlertDialog(dismissDialog)
        DialogType.WINDOW -> DialogWindow(windowDialogState, dismissDialog)
        DialogType.POPUP -> Popup()
    }
}

@Composable
private fun DialogButton(text: String, onClick: () -> Unit) {
    BoxWithTooltip(
        tooltip = {
            Surface(
                modifier = Modifier.shadow(4.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "Tooltip: $text",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    ) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text)
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun AlertDialog(onClickAndDismiss: () -> Unit) {
    AlertDialog(
        modifier = Modifier
            .width(300.dp)
            .height(100.dp)
            .border(width = 2.dp, MaterialTheme.colors.primary),
        dialogProvider = PopupAlertDialogProvider,
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
                    onClick = onClickAndDismiss,
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
private fun DialogWindow(dialogState: DialogState, onClickAndDismiss: () -> Unit) {
    Dialog(
        title = "Dialog Window",
        state = dialogState,
        onPreviewKeyEvent = {
            if (it.type == KeyEventType.KeyUp && it.key == Key.Escape) {
                onClickAndDismiss()
                true
            } else {
                false
            }
        },
        onCloseRequest = {
            onClickAndDismiss()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "This is dialog window",
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )
            DialogButton("Close", onClickAndDismiss)
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
                text = "Hello Popup",
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary
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