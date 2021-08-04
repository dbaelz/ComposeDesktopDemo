package de.dbaelz.compose.desktop.demo

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import de.dbaelz.compose.desktop.demo.theme.DesktopDemoTheme
import de.dbaelz.compose.desktop.demo.view.*

private var useDarkMode by mutableStateOf(false)

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(
            size = WindowSize(1280.dp, 840.dp)
        ),
        title = "Compose for Desktop Demo",
        resizable = true,
        onKeyEvent = {
            // Theme change on "CTRL + ALT + D". Only on KeyUp (== key released), not when pressed.
            if (it.type == KeyEventType.KeyUp && it.isCtrlPressed && it.isAltPressed && it.key == Key.D) {
                useDarkMode = !useDarkMode
                true
            } else {
                false
            }
        }
    ) {
        var screenState by remember { mutableStateOf(Screen.MAIN) }
        val navigateToMain = { screenState = Screen.MAIN }

        DesktopDemoTheme(withDarkTheme = useDarkMode) {
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
                    Screen.DIALOG -> DialogScreen(navigateToMain)
                    Screen.CLOSE_APP -> this@application.exitApplication()
                    Screen.ANIMATION -> AnimationScreen(navigateToMain)
                    Screen.CANVAS -> CanvasScreen(navigateToMain)
                    Screen.TIMER -> TimerScreen(navigateToMain)
                    Screen.TEXT_ANIMATION -> TextAnimationScreen(navigateToMain)
                    Screen.SHAPE -> ShapeScreen(navigateToMain)
                    Screen.CUSTOM_LAYOUT -> CustomLayoutScreen(navigateToMain)
                    Screen.TABS -> TabsScreen(navigateToMain)
                    Screen.SCAFFOLD -> ScaffoldScreen(navigateToMain) {
                        useDarkMode = !useDarkMode
                    }
                    Screen.EXPERIMENTS -> ExperimentScreen(navigateToMain)
                    Screen.PUZZLER -> PuzzlerScreen(navigateToMain)
                    Screen.MOUSE -> MouseScreen(navigateToMain)
                    Screen.KEYBOARD -> KeyboardScreen(navigateToMain)
                }
            }
        }
    }
}

private fun createMenu(): MainMenuModel {
    return MainMenuModel(
        listOf(
            MainMenuModel.Item.Entry(name = "Playground", targetScreen = Screen.PLAYGROUND),
            MainMenuModel.Item.Entry(name = "Puzzler", targetScreen = Screen.PUZZLER),
            MainMenuModel.Item.Entry(name = "Experiments", targetScreen = Screen.EXPERIMENTS),
            MainMenuModel.Item.Separator,

            MainMenuModel.Item.Entry(name = "Dialog", targetScreen = Screen.DIALOG),
            MainMenuModel.Item.Entry(name = "Tabs", targetScreen = Screen.TABS),
            MainMenuModel.Item.Entry(name = "Scaffold", targetScreen = Screen.SCAFFOLD),
            MainMenuModel.Item.Entry(
                name = "Mouse",
                targetScreen = Screen.MOUSE
            ),
            MainMenuModel.Item.Entry(
                name = "Keyboard",
                targetScreen = Screen.KEYBOARD
            ),
            MainMenuModel.Item.Separator,

            MainMenuModel.Item.Entry(name = "Animation", targetScreen = Screen.ANIMATION),
            MainMenuModel.Item.Entry(name = "Text Animation", targetScreen = Screen.TEXT_ANIMATION),
            MainMenuModel.Item.Entry(
                name = "Shapes",
                icon = Icons.Default.Star,
                targetScreen = Screen.SHAPE
            ),
            MainMenuModel.Item.Separator,

            MainMenuModel.Item.Entry(name = "Canvas", targetScreen = Screen.CANVAS),
            MainMenuModel.Item.Entry(name = "(Custom) Layout", targetScreen = Screen.CUSTOM_LAYOUT),
            MainMenuModel.Item.Entry(
                name = "Timer",
                icon = Icons.Default.Refresh,
                targetScreen = Screen.TIMER
            ),
            MainMenuModel.Item.Separator,

            MainMenuModel.Item.Entry(
                name = "Close App",
                icon = Icons.Default.ExitToApp,
                targetScreen = Screen.CLOSE_APP
            )
        )
    )
}

enum class Screen {
    MAIN,
    PLAYGROUND,
    DIALOG,
    ANIMATION,
    CANVAS,
    CLOSE_APP,
    TIMER,
    TEXT_ANIMATION,
    SHAPE,
    CUSTOM_LAYOUT,
    TABS,
    SCAFFOLD,
    EXPERIMENTS,
    PUZZLER,
    MOUSE,
    KEYBOARD,
}
