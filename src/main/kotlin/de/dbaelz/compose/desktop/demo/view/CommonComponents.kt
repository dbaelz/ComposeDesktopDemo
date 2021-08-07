package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Screen(
    navigation: @Composable ColumnScope.() -> Unit,
    items: List<@Composable ColumnScope.() -> Unit>,
) {
    val scrollState = rememberScrollState(0)

    Column(modifier = Modifier.fillMaxSize()) {
        navigation()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            items.forEach { composable ->
                composable()

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun ScreenTopBar(
    title: String = "Placeholder Screen",
    onBackNavigation: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            Spacer(Modifier.width(8.dp))
            Icon(
                Icons.Default.ArrowBack, "navigate back",
                modifier = Modifier.clickable {
                    onBackNavigation()
                })
        },
        actions = actions
    )
}

@Composable
fun ScreenItemsColumn(
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
        Spacer(Modifier.height(32.dp))

        items.forEach { composable ->
            composable()

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}