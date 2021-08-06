package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalFocusManager
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
                FocusAwareTextField(
                    textFieldModel = TextFieldModel(
                        "Press \"CTRL + D\" for a sample greeting",
                        "Hello there!", "Greetings", "Leave us a greeting"
                    ),
                    textFieldValue = state,
                ) { state = it }
            },
            {
                var state by remember { mutableStateOf(TextFieldValue()) }
                FocusAwareTextField(
                    textFieldModel = TextFieldModel(
                        "Press \"CTRL + D\" for a sample message",
                        "This is a very important message.",
                        "Message", "Your message"
                    ),
                    textFieldValue = state
                ) { state = it }
            },
        )
    )
}

@ExperimentalComposeUiApi
@Composable
private fun FocusAwareTextField(
    textFieldModel: TextFieldModel,
    textFieldValue: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val focused =
        MaterialTheme.colors.secondary to RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    val notFocused = MaterialTheme.colors.primary to MaterialTheme.shapes.small.copy(
        bottomEnd = ZeroCornerSize,
        bottomStart = ZeroCornerSize
    )
    var border by remember { mutableStateOf(notFocused) }

    Text(text = textFieldModel.shortCutText)

    TextField(
        modifier = Modifier
            .width(400.dp)
            .border(2.dp, border.first, border.second)
            .onPreviewKeyEvent {
                if (it.type == KeyEventType.KeyUp) return@onPreviewKeyEvent false

                when {
                    it.isCtrlPressed && it.key == Key.D -> {
                        onTextChanged(TextFieldValue(textFieldModel.sampleText))
                        true
                    }
                    it.key == Key.Tab -> {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                    else -> {
                        false
                    }
                }
            }
            .onFocusChanged {
                border = if (it.isFocused) focused else notFocused
            },
        shape = border.second,
        value = textFieldValue,
        onValueChange = onTextChanged,
        label = { Text(textFieldModel.label) },
        placeholder = { Text(textFieldModel.placeholder) }
    )
}

private data class TextFieldModel(
    val shortCutText: String,
    val sampleText: String,
    val label: String,
    val placeholder: String
)
