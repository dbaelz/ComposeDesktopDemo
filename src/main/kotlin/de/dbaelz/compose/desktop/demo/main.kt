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
    val navigateToMain = { screenState = Screen.MAIN}

    DesktopDemoTheme {
        Crossfade(
            targetState = screenState,
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearOutSlowInEasing
            )
        ) { newState ->
            when (newState) {
                Screen.MAIN -> MainMenuScreen(createMenu()) { screenState = it }
                Screen.PLAYGROUND -> PlaygroundScreen(navigateToMain)
                Screen.DIFF_TOOL -> DiffToolScreen(DiffUtils(), localAppWindow, navigateToMain)
                Screen.ALERT_DIALOG -> AlertDialogScreen(navigateToMain)
                Screen.CLICKABLE_TEXT -> ClickableTextScreen(navigateToMain)
                Screen.CLOSE_APP -> AppManager.focusedWindow?.close()
                Screen.ANIMATION -> AnimationScreen(navigateToMain)
                Screen.CANVAS -> CanvasScreen(navigateToMain)
                Screen.TIMER -> TimerScreen(navigateToMain)
            }
        }
    }
}

fun createMenu(): MainMenuModel {
    return MainMenuModel(
        listOf(
            MainMenuModel.Entry("Playground", Screen.PLAYGROUND),
            MainMenuModel.Entry("Diff Tool", Screen.DIFF_TOOL),
            MainMenuModel.Entry("Alarm Dialog", Screen.ALERT_DIALOG),
            MainMenuModel.Entry("Clickable Text", Screen.CLICKABLE_TEXT),
            MainMenuModel.Entry("Animation", Screen.ANIMATION),
            MainMenuModel.Entry("Canvas", Screen.CANVAS),
            MainMenuModel.Entry("Timer", Screen.TIMER),
            MainMenuModel.Entry("Close App", Screen.CLOSE_APP)
        )
    )
}

enum class Screen {
    MAIN,
    PLAYGROUND,
    DIFF_TOOL,
    ALERT_DIALOG,
    CLICKABLE_TEXT,
    ANIMATION,
    CANVAS,
    CLOSE_APP,
    TIMER
}
