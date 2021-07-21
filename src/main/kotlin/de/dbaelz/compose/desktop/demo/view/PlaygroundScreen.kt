package de.dbaelz.compose.desktop.demo.view

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun PlaygroundScreen(onBackNavigation: () -> Unit) {
    MenuColumn(
        onBackNavigation, listOf(
            {
                Text("Add content here")
            },
        )
    )
}
