package de.dbaelz.compose.desktop.demo.view

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun MouseKeyboardScreen(onBackNavigation: () -> Unit) {
    MenuColumn(
        onBackNavigation, listOf(
            {
                Text("Mouse & Keyboard", style = MaterialTheme.typography.h1)
            },
        )
    )
}
