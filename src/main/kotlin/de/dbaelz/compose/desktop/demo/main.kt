package de.dbaelz.compose.desktop.demo

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.desktop.AppManager
import androidx.compose.desktop.LocalAppWindow
import androidx.compose.desktop.Window
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntSize
import de.dbaelz.compose.desktop.demo.feature.DiffUtils
import de.dbaelz.compose.desktop.demo.view.*


@ExperimentalAnimationApi
fun main() = Window(
    title = "Compose for Desktop Demo",
    size = IntSize(1024, 786),
    resizable = true,
    undecorated = false
) {
    var screenState by remember { mutableStateOf(Screen.MAIN) }
    val localAppWindow = LocalAppWindow.current

    DesktopDemoTheme {
        Crossfade(
            targetState = screenState,
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearOutSlowInEasing
            )
        ) { newState ->
            when (newState) {
                Screen.MAIN -> {
                    MainMenuScreen(
                        MainMenuModel(
                            listOf(
                                MainMenuModel.Entry("Diff Tool", Screen.DIFF_TOOL),
                                MainMenuModel.Entry("Alarm Dialog", Screen.ALERT_DIALOG),
                                MainMenuModel.Entry("Clickable Text", Screen.CLICKABLE_TEXT),
                                MainMenuModel.Entry("Animation", Screen.ANIMATION),
                                MainMenuModel.Entry("Canvas", Screen.CANVAS),
                                MainMenuModel.Entry("Close App", Screen.CLOSE_APP)
                            )
                        )
                    ) { screenState = it }
                }
                Screen.DIFF_TOOL -> DiffToolScreen(DiffUtils(), localAppWindow) {
                    screenState = Screen.MAIN
                }
                Screen.ALERT_DIALOG -> AlertDialogScreen { screenState = Screen.MAIN }
                Screen.CLICKABLE_TEXT -> ClickableTextScreen { screenState = Screen.MAIN }
                Screen.CLOSE_APP -> AppManager.focusedWindow?.close()
                Screen.ANIMATION -> AnimationScreen { screenState = Screen.MAIN }
                Screen.CANVAS -> CanvasScreen { screenState = Screen.MAIN }
            }
        }
    }
}

enum class Screen {
    MAIN,
    DIFF_TOOL,
    ALERT_DIALOG,
    CLICKABLE_TEXT,
    ANIMATION,
    CANVAS,
    CLOSE_APP
}
