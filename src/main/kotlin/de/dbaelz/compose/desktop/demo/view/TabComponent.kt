package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun TabRowComponent(
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

data class Tab(
    val type: Type,
    val text: String,
    val icon: ImageVector? = null
) {
    enum class Type {
        HOME,
        TAB_STYLE,
        CUSTOM_TABS,
        LEADING_ICON_TABS
    }
}

data class TabStyle(val withText: Boolean = true, val withIcon: Boolean = true)
