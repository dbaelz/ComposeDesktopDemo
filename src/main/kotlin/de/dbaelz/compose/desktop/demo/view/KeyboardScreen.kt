package de.dbaelz.compose.desktop.demo.view

import androidx.compose.runtime.Composable

@Composable
fun KeyboardScreen(onBackNavigation: () -> Unit) {
    MenuColumn(
        onBackNavigation, listOf {

        }
    )
}