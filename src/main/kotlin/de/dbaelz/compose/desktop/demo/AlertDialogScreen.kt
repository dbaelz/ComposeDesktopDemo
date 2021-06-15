package de.dbaelz.compose.desktop.demo

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.v1.DialogProperties

@Composable
fun AlertDialogScreen(onClickAndDismiss: () -> Unit) {
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