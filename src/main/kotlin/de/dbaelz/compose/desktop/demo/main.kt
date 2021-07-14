package de.dbaelz.compose.desktop.demo

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.desktop.AppManager
import androidx.compose.desktop.Window
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.IntSize
import de.dbaelz.compose.desktop.demo.view.*


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun main() = Window(
    title = "Compose for Desktop Demo",
    size = IntSize(1024, 768),
    resizable = true,
    undecorated = false
) {
    var screenState by remember { mutableStateOf(Screen.MAIN) }
    val navigateToMain = { screenState = Screen.MAIN }

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
                Screen.DIALOG -> DialogScreen(navigateToMain)
                Screen.CLOSE_APP -> AppManager.focusedWindow?.close()
                Screen.ANIMATION -> AnimationScreen(navigateToMain)
                Screen.CANVAS -> CanvasScreen(navigateToMain)
                Screen.TIMER -> TimerScreen(navigateToMain)
                Screen.TEXT_ANIMATION -> TextAnimationScreen(navigateToMain)
                Screen.SHAPE -> ShapeScreen(navigateToMain)
                Screen.CUSTOM_LAYOUT -> CustomLayoutScreen(navigateToMain)
                Screen.TABS -> TabsScreen(navigateToMain)
                Screen.SCAFFOLD -> ScaffoldScreen(navigateToMain)
            }
        }
    }
}

private fun createMenu(): MainMenuModel {
    return MainMenuModel(
        listOf(
            MainMenuModel.Item.Entry(name = "Playground", targetScreen = Screen.PLAYGROUND),
            MainMenuModel.Item.Entry(name = "Dialog", targetScreen = Screen.DIALOG),
            MainMenuModel.Item.Entry(name = "Tabs", targetScreen = Screen.TABS),
            MainMenuModel.Item.Entry(name = "Scaffold", targetScreen = Screen.SCAFFOLD),
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
            MainMenuModel.Item.Entry(name = "Custom Layout", targetScreen = Screen.CUSTOM_LAYOUT),
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
}
