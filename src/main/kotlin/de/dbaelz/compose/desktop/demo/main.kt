package de.dbaelz.compose.desktop.demo

import androidx.compose.desktop.LocalAppWindow
import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import de.dbaelz.compose.desktop.demo.difftool.DiffToolScreen


fun main() = Window(
    title = "Compose for Desktop Demo",
    resizable = true,
    undecorated = true
) {
    var screenState by remember { mutableStateOf(Screen.MAIN) }
    val localAppWindow = LocalAppWindow.current

    MaterialTheme {
        when (screenState) {
            Screen.MAIN -> {
                MainMenuScreen(
                    MainMenuModel(
                        listOf(
                            MainMenuModel.Entry("Diff Tool", { screenState = Screen.DIFF_TOOL })
                        )
                    )
                )
            }
            Screen.DIFF_TOOL -> DiffToolScreen(localAppWindow)
        }
    }
}

enum class Screen {
    MAIN,
    DIFF_TOOL
}
