package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.unit.dp
import javax.swing.JButton

@Composable
fun InteropScreen(onBackNavigation: () -> Unit) {
    Screen(
        { ScreenTopBar("Interoperability with Swing", onBackNavigation) },
        listOf(
            {
                SwingJButton()
            }
        )
    )
}

@Composable
private fun SwingJButton() {
    var counter by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SwingPanel(
            background = MaterialTheme.colors.background,
            modifier = Modifier.width(400.dp).height(50.dp),
            factory = {
                return@SwingPanel JButton("Increase Counter").apply {
                    addActionListener { counter++ }
                }
            }
        )

        Spacer(Modifier.height(8.dp))

        CompositionLocalProvider(
            LocalTextStyle provides MaterialTheme.typography.h5,
            LocalContentColor provides MaterialTheme.colors.onBackground
        ) {
            Text("Counter: $counter")
        }
    }

}
