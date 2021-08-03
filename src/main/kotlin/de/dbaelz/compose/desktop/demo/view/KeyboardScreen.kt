package de.dbaelz.compose.desktop.demo.view

import androidx.compose.runtime.Composable

@Composable
fun KeyboardScreen(onBackNavigation: () -> Unit) {
    Screen(
        { ScreenTopBar("Keyboard Input and Focus", onBackNavigation) },
        listOf {

        }
    )
}