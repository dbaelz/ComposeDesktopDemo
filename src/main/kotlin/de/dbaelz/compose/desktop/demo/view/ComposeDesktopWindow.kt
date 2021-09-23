package de.dbaelz.compose.desktop.demo.view

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalDesktopApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.rememberWindowState
import de.dbaelz.compose.desktop.demo.theme.DesktopDemoTheme
import de.dbaelz.compose.desktop.demo.theme.TourneyTypography

@ExperimentalDesktopApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun ComposeDesktopWindow(
    width: Dp,
    height: Dp,
    onCloseRequest: () -> Unit
) {
    var useDarkMode by mutableStateOf(false)
    var useDefaultTypography by mutableStateOf(true)

    val defaultWindowSize = WindowSize(width, height)
    val windowState = rememberWindowState(
        placement = WindowPlacement.Floating,
        size = defaultWindowSize
    )

    Window(
        onCloseRequest = onCloseRequest,
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
                    Screen.MAIN -> MainMenuScreen(createMenu()) {
                        screenState = it
                    }
                    Screen.PLAYGROUND -> PlaygroundScreen(
                        navigateToMain
                    )
                    Screen.DIALOG -> DialogScreen(navigateToMain)
                    Screen.CANVAS -> CanvasScreen(navigateToMain)
                    Screen.TIMER -> TimerScreen(navigateToMain)
                    Screen.TEXT_ANIMATION -> TextAnimationScreen(
                        navigateToMain
                    )
                    Screen.SHAPE -> ShapeScreen(navigateToMain)
                    Screen.CUSTOM_LAYOUT -> CustomLayoutScreen(
                        navigateToMain
                    )
                    Screen.TABS -> TabsScreen(navigateToMain)
                    Screen.SCAFFOLD -> ScaffoldScreen(navigateToMain) {
                        useDarkMode = !useDarkMode
                    }
                    Screen.EXPERIMENTS -> ExperimentScreen(
                        navigateToMain
                    )
                    Screen.PUZZLER -> PuzzlerScreen(navigateToMain)
                    Screen.MOUSE -> MouseScreen(navigateToMain)
                    Screen.KEYBOARD -> KeyboardScreen(navigateToMain)
                    Screen.INTEROP -> InteropScreen(navigateToMain)
                }
            }
        }
    }
}

private fun createMenu(): List<MenuItem> {
    return listOf(
        MenuItem(name = "Playground", targetScreen = Screen.PLAYGROUND),
        MenuItem(name = "Puzzler", targetScreen = Screen.PUZZLER),
        MenuItem(name = "Experiments", targetScreen = Screen.EXPERIMENTS),
        MenuItem(name = "Dialog", targetScreen = Screen.DIALOG),
        MenuItem(name = "Tabs", targetScreen = Screen.TABS),
        MenuItem(name = "Scaffold", targetScreen = Screen.SCAFFOLD),

        MenuItem(name = "Mouse", targetScreen = Screen.MOUSE),
        MenuItem(name = "Keyboard", targetScreen = Screen.KEYBOARD),
        MenuItem(name = "Interop", targetScreen = Screen.INTEROP),

        MenuItem(name = "Text Animation", targetScreen = Screen.TEXT_ANIMATION),
        MenuItem(name = "Shapes", icon = Icons.Default.Star, targetScreen = Screen.SHAPE),
        MenuItem(name = "Canvas", targetScreen = Screen.CANVAS),
        MenuItem(name = "(Custom) Layout", targetScreen = Screen.CUSTOM_LAYOUT),
        MenuItem(name = "Timer", icon = Icons.Default.Refresh, targetScreen = Screen.TIMER)
    )
}

enum class Screen {
    MAIN,
    PLAYGROUND,
    DIALOG,
    CANVAS,
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