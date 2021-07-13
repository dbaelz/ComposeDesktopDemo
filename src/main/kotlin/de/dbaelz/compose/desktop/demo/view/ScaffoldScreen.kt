package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ScaffoldScreen(onBackNavigation: () -> Unit) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        },
        drawerContent = { Drawer() },
        content = { Content() }
    )
}

@Composable
private fun TopBar(onMenuIconClicked: () -> Unit) {
    TopAppBar {
        Icon(Icons.Default.Menu, null,
            modifier = Modifier.clickable {
                onMenuIconClicked()
            })

        Spacer(Modifier.width(8.dp))

        Text("My Scaffold Example")
    }
}

@Composable
private fun Drawer() {
    Column {
        Text("Drawer")
    }
}

@Composable
private fun Content() {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.primaryVariant))
}