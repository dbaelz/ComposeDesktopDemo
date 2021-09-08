package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.unit.dp
import java.awt.Dimension
import javax.swing.*

@Composable
fun InteropScreen(onBackNavigation: () -> Unit) {
    Screen(
        { ScreenTopBar("Interoperability with Swing", onBackNavigation) },
        listOf(
            { SwingJButton() },
            { SwingWindowButton() },
            { ComposableInsideSwing() },
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
                return@SwingPanel JButton("JButton inside Composable").apply {
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
private fun SwingWindowButton() {
    var windowOpened by remember { mutableStateOf(false) }

    Button(onClick = {
        windowOpened = true
    }) {
        Text("Open Swing Window")

        if (windowOpened) {
            windowOpened = false
            SwingWindow()
        }
    }
}

@Composable
private fun SwingWindow() {
    SwingPanel(
        background = MaterialTheme.colors.background,
        modifier = Modifier.width(400.dp).height(50.dp),
        factory = {
            JPanel().apply {
                JFrame("Hello World Swing").apply {
                    preferredSize = Dimension(320, 200)

                    contentPane.add(JLabel("Hello World", SwingConstants.CENTER))

                    pack()
                    isVisible = true
                }
            }
        }
    )
}

@Composable
private fun ComposableInsideSwing() {
    var counter by remember { mutableStateOf(0) }

    SwingPanel(
        background = MaterialTheme.colors.surface,
        modifier = Modifier.width(400.dp).height(200.dp),
        factory = {
            val composePanel = ComposePanel()

            composePanel.setContent {
                Counter(counter) { counter++ }
            }

            return@SwingPanel JPanel().add(composePanel)
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
                Text("Composable inside Swing")
            }

            Spacer(Modifier.height(8.dp))

            Text("Counter: $counter")
        }
    }
}
