package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun TabsScreen(onBackNavigation: () -> Unit) {
    val tabStyle by remember { mutableStateOf(TabStyle()) }
    var tabType by remember { mutableStateOf(Tab.Type.HOME) }

    val tabs = listOf<Tab>(
        Tab(Tab.Type.HOME, "Home", Icons.Default.Home) { tabType = Tab.Type.HOME },
        Tab(Tab.Type.TAB_STYLE, "TabStyle", Icons.Default.Settings) {
            tabType = Tab.Type.TAB_STYLE
        },
    )

    Column {
        CustomTabRow(tabs, tabStyle) { tabType = it }

        when (tabType) {
            Tab.Type.HOME -> HomeTab()
            Tab.Type.TAB_STYLE -> TabStyleTab()
        }
    }
}

@Composable
private fun CustomTabRow(
    tabs: List<Tab>,
    tabStyle: TabStyle,
    onTabSelected: (Tab.Type) -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    TabRow(selectedTabIndex) {
        tabs.forEachIndexed { index, tab ->
            Tab(selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    onTabSelected(tab.type)
                },

                text = if (tabStyle.withText) {
                    { Text(tab.text) }
                } else null,
                icon = if (tabStyle.withIcon) {
                    { Icon(tab.icon ?: Icons.Default.Home, null) }
                } else null
            )
        }
    }
}

@Composable
fun HomeTab() {
    Text(
        text = "Home Tab",
        style = MaterialTheme.typography.h1
    )
}

@Composable
fun TabStyleTab() {
    Text(
        text = "Tab Style Tab",
        style = MaterialTheme.typography.h1
    )
}


data class Tab(
    val type: Type,
    val text: String,
    val icon: ImageVector? = null,
    val onTabSelected: () -> Unit = {}
) {
    enum class Type {
        HOME,
        TAB_STYLE
    }
}

data class TabStyle(val withText: Boolean = true, val withIcon: Boolean = true)
