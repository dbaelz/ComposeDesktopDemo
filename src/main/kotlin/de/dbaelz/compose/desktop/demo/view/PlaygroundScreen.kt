package de.dbaelz.compose.desktop.demo.view

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun PlaygroundScreen(onBackNavigation: () -> Unit) {
    Screen(
        { ScreenTopBar("Playground", onBackNavigation) },
        listOf(
            {
                Text("Playground", style = MaterialTheme.typography.h1)
            },
        )
    )
}
