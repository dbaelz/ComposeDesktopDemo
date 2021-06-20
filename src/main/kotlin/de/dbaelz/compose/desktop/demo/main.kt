package de.dbaelz.compose.desktop.demo

import androidx.compose.desktop.AppManager
import androidx.compose.desktop.LocalAppWindow
import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntSize
import de.dbaelz.compose.desktop.demo.feature.DiffUtils
import de.dbaelz.compose.desktop.demo.view.*


fun main() = Window(
    title = "Compose for Desktop Demo",
    size = IntSize(1024, 786),
    resizable = true,
    undecorated = false
) {
    var screenState by remember { mutableStateOf(Screen.MAIN) }
    val localAppWindow = LocalAppWindow.current

    MaterialTheme {
        when (screenState) {
            Screen.MAIN -> {
                MainMenuScreen(
                    MainMenuModel(
                        listOf(
                            MainMenuModel.Entry("Diff Tool", { screenState = Screen.DIFF_TOOL }),
                            MainMenuModel.Entry("Alarm Dialog", { screenState = Screen.ALERT_DIALOG }),
                            MainMenuModel.Entry("Clickable Text", { screenState = Screen.CLICKABLE_TEXT }),
                            MainMenuModel.Entry("Close App", { screenState = Screen.CLOSE_APP })
                        )
                    )
                )
            }
            Screen.DIFF_TOOL -> DiffToolScreen(DiffUtils(), localAppWindow) {
                screenState = Screen.MAIN
            }
            Screen.ALERT_DIALOG -> AlertDialogScreen { screenState = Screen.MAIN }
            Screen.CLICKABLE_TEXT -> ClickableTextScreen { screenState = Screen.MAIN }
            Screen.CLOSE_APP -> AppManager.focusedWindow?.close()
        }
    }
}

enum class Screen {
    MAIN,
    DIFF_TOOL,
    ALERT_DIALOG,
    CLICKABLE_TEXT,
    CLOSE_APP
}
