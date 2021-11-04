package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TabsScreen(onBackNavigation: () -> Unit) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var selectedTabType by remember { mutableStateOf(Type.HOME) }

    var tabStyle by remember { mutableStateOf(TabStyle()) }

    val tabs = listOf(
        Tab(Type.HOME, "Home", Icons.Default.Home),
        Tab(Type.TAB_STYLE, "Tab Style", Icons.Default.Settings),
        Tab(Type.CUSTOM_TABS, "Custom Tabs", Icons.Default.Create),
        Tab(Type.LEADING_ICON_TABS, "Leading Icon Tabs", Icons.Default.List),
        Tab(Type.SCROLLABLE_TABS, "Scrollable Tabs", Icons.Default.KeyboardArrowRight)
    )

    Column {
        ScreenTopBar("Shape", onBackNavigation)

        TabRowComponent(tabs, selectedTabIndex, tabStyle) { index, type ->
            selectedTabIndex = index
            selectedTabType = type
        }

        when (selectedTabType) {
            Type.HOME -> HomeTab(onBackNavigation)
            Type.TAB_STYLE -> TabStyleTab {
                tabStyle = it
            }
            Type.CUSTOM_TABS -> NestedCustomTabs(
                listOf(
                    "First", "Second", "Third"
                )
            )
            Type.LEADING_ICON_TABS -> NestedLeadingIconTab(
                listOf(
                    "Star" to Icons.Default.Star,
                    "Info" to Icons.Default.Info,
                )
            )
            Type.SCROLLABLE_TABS -> {
                val scrollableTabs = mutableListOf<String>()
                (0..15).forEach {
                    scrollableTabs.add("Tab $it")
                }

                ScrollableTabs(scrollableTabs)
            }
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

        Card(
            modifier = Modifier.fillMaxWidth(0.4f).padding(16.dp),
            elevation = 10.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Home Tab",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "With a card component",
                    style = MaterialTheme.typography.body1
                )

                Spacer(Modifier.height(8.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onBackNavigation
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "back"
                    )
                    Text("Back to main")
                }
            }
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
        color = MaterialTheme.colors.onBackground,
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
private fun NestedCustomTabs(tabs: List<String>) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var selectedTabText by remember { mutableStateOf(tabs[0]) }

    Box(Modifier.height(8.dp).fillMaxWidth().background(MaterialTheme.colors.primaryVariant))

    TabRow(
        selectedTabIndex,
        modifier = Modifier.requiredHeight(76.dp)
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    selectedTabText = tab
                },
                content = {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(84.dp)
                            .border(
                                width = 8.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        MaterialTheme.colors.secondary,
                                        MaterialTheme.colors.secondaryVariant
                                    )
                                ), shape = RectangleShape
                            )
                    ) {
                        Text(tab)
                    }
                }

            )
        }
    }

    StyledContentText(selectedTabText)
}

@Composable
private fun NestedLeadingIconTab(tabs: List<Pair<String, ImageVector>>) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var selectedTabText by remember { mutableStateOf(tabs[0].first) }

    Box(Modifier.height(8.dp).fillMaxWidth().background(MaterialTheme.colors.primaryVariant))

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

    StyledContentText(selectedTabText)
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

@Composable
private fun ScrollableTabs(tabs: List<String>) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var selectedTabText by remember { mutableStateOf(tabs[0]) }

    Box(Modifier.height(8.dp).fillMaxWidth().background(MaterialTheme.colors.primaryVariant))

    ScrollableTabRow(
        selectedTabIndex
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(selected = selectedTabIndex == index,
                modifier = Modifier.padding(8.dp),
                onClick = {
                    selectedTabIndex = index
                    selectedTabText = tab
                },
                content = {
                    Text(tab)
                }

            )
        }
    }

    StyledContentText(selectedTabText)
}

@Composable
private fun StyledContentText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h1,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.onBackground,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    )
}

private enum class Type {
    HOME,
    TAB_STYLE,
    CUSTOM_TABS,
    LEADING_ICON_TABS,
    SCROLLABLE_TABS,
}