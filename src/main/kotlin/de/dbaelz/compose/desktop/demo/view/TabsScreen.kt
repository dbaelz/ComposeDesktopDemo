package de.dbaelz.compose.desktop.demo.view

import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun TabsScreen(onBackNavigation: () -> Unit) {
    val tabs = listOf(
        "Home" to Icons.Default.Home,
        "Star" to Icons.Default.Star
    )

    CustomTabRow(tabs)
}

@Composable
fun CustomTabRow(
    tabs: List<Pair<String, ImageVector>>,
    withText: Boolean = true,
    withIcon: Boolean = true
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    TabRow(selectedTabIndex) {
        tabs.forEachIndexed { index, data ->
            Tab(selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                },

                text = if (withText) {
                    { Text(data.first) }
                } else null,
                icon = if (withIcon) {
                    { Icon(data.second, null) }
                } else null
            )
        }
    }
}
