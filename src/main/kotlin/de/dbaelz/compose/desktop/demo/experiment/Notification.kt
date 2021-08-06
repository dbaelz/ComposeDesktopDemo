package de.dbaelz.compose.desktop.demo.experiment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Notification
import androidx.compose.ui.window.Notifier
import androidx.compose.ui.window.TrayState


/**
 * Status: Doesn't work (alpha3) with Notifier and TrayState right now.
 * See https://kotlinlang.slack.com/archives/C01D6HTPATV/p1625824543436700
 */
@Composable
fun ExperimentNotification() {
    val notifier = Notifier()
    val trayState = TrayState()

    Column {
        Button(onClick = {
            // Uses SystemTray which is not supported on the platform
            notifier.notify("Notification Notifier", "Hello there!")
        }) { Text("Send via Notifier") }

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            trayState.sendNotification(Notification("Notification TrayState", "Hello there!"))
        }) {
            Text("Send via TrayState")
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            withNotifySend(Notification("Notification Notify Send", "Hello there!"))
        }) {
            Text("Send via Notify Send")
        }
    }
}

private fun withNotifySend(notification: Notification) {
    Runtime.getRuntime().exec(arrayOf("notify-send", notification.title, notification.message))
}