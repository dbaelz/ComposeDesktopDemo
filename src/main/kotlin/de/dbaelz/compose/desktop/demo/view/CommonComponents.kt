package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuColumn(
    onBackNavigation: () -> Unit,
    items: List<@Composable () -> Unit>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState(0)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BackButton(onBackNavigation)

        items.forEach { composable ->
            composable()

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}