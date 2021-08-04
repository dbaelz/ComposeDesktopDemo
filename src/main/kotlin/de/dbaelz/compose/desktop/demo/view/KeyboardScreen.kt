package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@ExperimentalComposeUiApi
@Composable
fun KeyboardScreen(onBackNavigation: () -> Unit) {
    Screen(
        { ScreenTopBar("Keyboard Input and Focus", onBackNavigation) },
        listOf(
            {
                var state by remember { mutableStateOf(TextFieldValue()) }

                Text(text = "Press \"CTRL + D\" for a sample text")

                TextField(
                    modifier = Modifier
                        .width(400.dp)
                        .onPreviewKeyEvent {
                            if (it.isCtrlPressed && it.key == Key.D) {
                                state = TextFieldValue("I am ...")
                                true
                            } else {
                                false
                            }
                        },
                    value = state,
                    onValueChange = { state = it },
                    label = { Text("Your Bio") },
                    placeholder = { Text("Tell us something about yourself") }
                )
            },
            {

            }
        )
    )
}

