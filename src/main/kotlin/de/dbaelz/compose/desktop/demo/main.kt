package de.dbaelz.compose.desktop.demo

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.application
import de.dbaelz.compose.desktop.demo.view.ComposeDesktopWindow

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun main() = application {
    ComposeDesktopWindow(1280.dp, 840.dp, ::exitApplication)
}
