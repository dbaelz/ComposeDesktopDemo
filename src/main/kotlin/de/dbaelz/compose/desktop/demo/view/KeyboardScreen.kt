package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun KeyboardScreen(onBackNavigation: () -> Unit) {
    Screen(
        { ScreenTopBar("Keyboard Input and Focus", onBackNavigation) },
        listOf(
            {
                val state = remember { mutableStateOf(TextFieldValue()) }

                TextField(
                    modifier = Modifier.width(400.dp),
                    value = state.value,
                    onValueChange = { state.value = it },
                    label = { Text("Your Bio") },
                    placeholder = { Text("Tell us something about yourself") }
                )
            },
            {

            }
        )
    )
}

