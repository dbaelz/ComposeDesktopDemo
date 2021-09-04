package de.dbaelz.compose.desktop.demo

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalDesktopApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import de.dbaelz.compose.desktop.demo.theme.DesktopDemoTheme
import de.dbaelz.compose.desktop.demo.theme.TourneyTypography
import de.dbaelz.compose.desktop.demo.view.*

@ExperimentalDesktopApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun main() = application {
    var useDarkMode by mutableStateOf(false)
    var useDefaultTypography by mutableStateOf(true)

    val defaultWindowSize = WindowSize(1280.dp, 840.dp)
    val windowState = rememberWindowState(
        placement = WindowPlacement.Floating,
        size = defaultWindowSize
    )

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "Compose for Desktop Demo",
        icon = painterResource("images/compose-logo.png"),
        resizable = true,
        onKeyEvent = {
            when {
                it.key == Key.F11 -> {
                    if (it.type == KeyEventType.KeyUp) return@Window false

                    if (windowState.placement == WindowPlacement.Fullscreen) {
                        windowState.placement = WindowPlacement.Floating
                        windowState.size = defaultWindowSize
                    } else {
                        windowState.placement = WindowPlacement.Fullscreen
                    }
                    true
                }
                it.isCtrlPressed && it.key == Key.T -> {
                    // Theme change on "CTRL + T". Only on KeyUp (== key released), not when pressed.
                    if (it.type == KeyEventType.KeyUp) {
                        useDarkMode = !useDarkMode
                    }
                    true
                }
                it.isCtrlPressed && it.key == Key.F -> {
                    // Font change on "CTRL + F". Only on KeyUp (== key released), not when pressed.
                    if (it.type == KeyEventType.KeyUp) {
                        useDefaultTypography = !useDefaultTypography
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }
    ) {
        var screenState by remember { mutableStateOf(Screen.MAIN) }
        val navigateToMain = { screenState = Screen.MAIN }

        DesktopDemoTheme(
            withDarkTheme = useDarkMode,
            typography = if (useDefaultTypography) MaterialTheme.typography else TourneyTypography
        ) {
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
                    Screen.INTEROP -> InteropScreen(navigateToMain)
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

            MainMenuModel.Item.Separator,

            MainMenuModel.Item.Entry(
                name = "Mouse",
                targetScreen = Screen.MOUSE
            ),
            MainMenuModel.Item.Entry(
                name = "Keyboard",
                targetScreen = Screen.KEYBOARD
            ),
            MainMenuModel.Item.Entry(name = "Interop", targetScreen = Screen.INTEROP),
            MainMenuModel.Item.Separator,

            MainMenuModel.Item.Entry(name = "Text Animation", targetScreen = Screen.TEXT_ANIMATION),
            MainMenuModel.Item.Entry(
                name = "Shapes",
                icon = Icons.Default.Star,
                targetScreen = Screen.SHAPE
            ),
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
    INTEROP,
}
