package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TabsScreen(onBackNavigation: () -> Unit) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var selectedTabType by remember { mutableStateOf(Tab.Type.HOME) }

    var tabStyle by remember { mutableStateOf(TabStyle()) }

    val tabs = listOf(
        Tab(Tab.Type.HOME, "Home", Icons.Default.Home) { selectedTabType = Tab.Type.HOME },
        Tab(Tab.Type.TAB_STYLE, "TabStyle", Icons.Default.Settings) {
            selectedTabType = Tab.Type.TAB_STYLE
        },
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
    val icon: ImageVector? = null,
    val onTabSelected: () -> Unit = {}
) {
    enum class Type {
        HOME,
        TAB_STYLE
    }
}

data class TabStyle(val withText: Boolean = true, val withIcon: Boolean = true)
