package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.unit.dp
import javax.swing.JButton
import javax.swing.JFrame

@Composable
fun InteropScreen(onBackNavigation: () -> Unit) {
    Screen(
        { ScreenTopBar("Interoperability with Swing", onBackNavigation) },
        listOf(
            {
                SwingJButton()
            },
            {
                ComposableInsideSwing()
            },
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

@Composable
private fun ComposableInsideSwing() {
    var counter by remember { mutableStateOf(0) }

    SwingPanel(
        background = MaterialTheme.colors.primary,
        modifier = Modifier.width(400.dp).height(200.dp),
        factory = {
            val composePanel = ComposePanel()

            composePanel.setContent {
                Counter(counter) { counter++ }
            }

            return@SwingPanel JFrame().add(composePanel)
        }
    )
}

@Composable
private fun Counter(counter: Int, onClick: () -> Unit) {
    CompositionLocalProvider(
        LocalTextStyle provides MaterialTheme.typography.h5,
        LocalContentColor provides MaterialTheme.colors.onBackground
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onClick) {
                Text("Increase Counter")
            }

            Spacer(Modifier.height(8.dp))

            Text("Counter: $counter")
        }
    }
}
