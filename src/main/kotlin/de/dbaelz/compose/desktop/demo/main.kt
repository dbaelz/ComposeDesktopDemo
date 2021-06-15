package de.dbaelz.compose.desktop.demo

import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


fun main() = Window(
    title = "Compose for Desktop Demo",
    resizable = true,
    undecorated = true
) {
    var screenState by remember { mutableStateOf(Screen.MAIN) }

    MaterialTheme {
        when (screenState) {
            Screen.MAIN -> {
                MainMenuScreen()
            }
        }
    }
}

enum class Screen {
    MAIN
}
