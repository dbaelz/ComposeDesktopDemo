package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun TabsScreen(onBackNavigation: () -> Unit) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var selectedTabType by remember { mutableStateOf(Tab.Type.HOME) }

    var tabStyle by remember { mutableStateOf(TabStyle()) }

    val tabs = listOf(
        Tab(Tab.Type.HOME, "Home", Icons.Default.Home),
        Tab(Tab.Type.TAB_STYLE, "Tab Style", Icons.Default.Settings),
        Tab(Tab.Type.LEADING_ICON_TABS, "Leading Icon Tabs", Icons.Default.List)
    )

    Column {
        CustomTabRow(tabs, selectedTabIndex, tabStyle) { index, type ->
            selectedTabIndex = index
            selectedTabType = type
        }

        when (selectedTabType) {
            Tab.Type.HOME -> HomeTab(onBackNavigation)
            Tab.Type.TAB_STYLE -> TabStyleTab {
                tabStyle = it
            }
            Tab.Type.LEADING_ICON_TABS -> NestedLeadingIconTab(
                listOf(
                    "Star" to Icons.Default.Star,
                    "Info" to Icons.Default.Info,
                )
            )
        }
    }
}

@Composable
private fun CustomTabRow(
    tabs: List<Tab>,
    selectedTabIndex: Int,
    tabStyle: TabStyle,
    onTabSelected: (Int, Tab.Type) -> Unit
) {
    TabRow(
        selectedTabIndex,
        modifier = Modifier.requiredHeight(76.dp)
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(selected = selectedTabIndex == index,
                onClick = { onTabSelected(index, tab.type) },
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
private fun HomeTab(onBackNavigation: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))

        Text(
            text = "Home Tab",
            style = MaterialTheme.typography.h1
        )

        Spacer(Modifier.height(16.dp))

        Button(onBackNavigation) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "back"
            )
            Text("Back to menu")
        }
    }

}

@Composable
private fun TabStyleTab(
    onTabStyleChanged: (TabStyle) -> Unit
) {
    Spacer(Modifier.height(16.dp))

    Text(
        "Select Tab Style",
        style = MaterialTheme.typography.h1,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        TabStyleButton("Text & Icon") {
            onTabStyleChanged(
                TabStyle(
                    withText = true,
                    withIcon = true
                )
            )
        }

        Spacer(Modifier.width(16.dp))

        TabStyleButton("Text only") {
            onTabStyleChanged(
                TabStyle(
                    withText = true,
                    withIcon = false
                )
            )
        }

        Spacer(Modifier.width(16.dp))

        TabStyleButton("Icon only") {
            onTabStyleChanged(
                TabStyle(
                    withText = false,
                    withIcon = true
                )
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun NestedLeadingIconTab(tabs: List<Pair<String, ImageVector>>) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var selectedTabText by remember { mutableStateOf(tabs[0].first) }

    TabRow(
        selectedTabIndex,
        modifier = Modifier.requiredHeight(76.dp)
    ) {
        tabs.forEachIndexed { index, tab ->
            LeadingIconTab(selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    selectedTabText = tab.first
                },
                text = { Text(tab.first) },
                icon = { Icon(tab.second, null) }
            )
        }
    }

    Text(
        text = selectedTabText,
        style = MaterialTheme.typography.h1,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    )
}

@Composable
private fun TabStyleButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CutCornerShape(16.dp)
    ) {
        Text(
            text,
            style = MaterialTheme.typography.h4
        )
    }
}


data class Tab(
    val type: Type,
    val text: String,
    val icon: ImageVector? = null
) {
    enum class Type {
        HOME,
        TAB_STYLE,
        LEADING_ICON_TABS
    }
}

data class TabStyle(val withText: Boolean = true, val withIcon: Boolean = true)
