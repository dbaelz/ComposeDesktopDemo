package de.dbaelz.compose.desktop.demo

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import de.dbaelz.compose.desktop.demo.view.ComposeDesktopWindow

fun main() = application {
    ComposeDesktopWindow(1280.dp, 840.dp, ::exitApplication)
}
