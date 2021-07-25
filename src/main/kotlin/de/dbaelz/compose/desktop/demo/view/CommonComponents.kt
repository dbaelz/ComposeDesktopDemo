package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuColumn(
    onBackNavigation: () -> Unit,
    items: List<@Composable ColumnScope.() -> Unit>,
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