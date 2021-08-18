package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.ContextMenuArea
import androidx.compose.foundation.ContextMenuDataProvider
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.ContextMenuItem
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import java.util.*

@ExperimentalFoundationApi
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
                        sampleDescription = "greeting",
                        sampleText = "Hello there!",
                        label = "Greetings",
                        placeholder = "Leave us a greeting"
                    ),
                    textFieldValue = state,
                ) { state = it }
            },
            {
                var state by remember { mutableStateOf(TextFieldValue()) }
                FocusAwareTextField(
                    textFieldModel = TextFieldModel(
                        sampleDescription = "message",
                        sampleText = "This is a very important message.",
                        label = "Message", placeholder = "Your message"
                    ),
                    textFieldValue = state
                ) { state = it }
            },
            {
                var text by remember { mutableStateOf("") }
                ContextMenuText(text) { text = it }
            },
        )
    )
}

@ExperimentalFoundationApi
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

    CompositionLocalProvider(
        LocalContentColor provides MaterialTheme.colors.onBackground
    ) {
        Text(text = "Press \"CTRL + D\" for a sample ${textFieldModel.sampleDescription}")

        ContextMenuDataProvider(
            items = {
                listOf(
                    ContextMenuItem(textFieldModel.sampleDescription.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    }) {
                        onTextChanged(TextFieldValue(textFieldModel.sampleText))
                    }
                )
            }
        ) {
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
    }
}

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
private fun ContextMenuText(
    text: String,
    onContextMenuItemSelected: (String) -> Unit
) {
    ContextMenuArea(items = {
        listOf(
            ContextMenuItem("Item 1") { onContextMenuItemSelected("Item 1") },
            ContextMenuItem("Item 2") { onContextMenuItemSelected("Item 2") }
        )
    }) {
        Text("Click right for ContextMenu: $text")
    }
}

@Immutable
private data class TextFieldModel(
    val sampleDescription: String,
    val sampleText: String,
    val label: String,
    val placeholder: String
)
