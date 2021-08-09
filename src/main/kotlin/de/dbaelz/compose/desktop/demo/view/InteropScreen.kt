package de.dbaelz.compose.desktop.demo.view

import androidx.compose.runtime.Composable

@Composable
fun InteropScreen(onBackNavigation: () -> Unit) {
    Screen(
        { ScreenTopBar("Interoperability with Swing", onBackNavigation) },
        listOf()
    )
}