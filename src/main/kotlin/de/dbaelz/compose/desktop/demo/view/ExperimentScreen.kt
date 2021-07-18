package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import de.dbaelz.compose.desktop.demo.experiment.ExperimentNotification

/**
 * Used for (non-working) experiments. Or stuff that is bleeding edge or maybe not
 * optimal implementation, but worth to test out.
 */
@Composable
fun ExperimentScreen(onBackNavigation: () -> Unit) {
    MenuColumn(onBackNavigation, listOf {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Notifications", style = MaterialTheme.typography.h4)
            ExperimentNotification() }
        }
    )
}