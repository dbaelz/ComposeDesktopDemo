package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
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
        drawerBackgroundColor = MaterialTheme.colors.background.copy(alpha = 0.8f),
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
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        (0..10).forEach {
            DrawerEntry(Icons.Default.Star, "Drawer Entry $it")
        }
    }
}

@Composable
private fun DrawerEntry(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier.padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colors.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
private fun Content() {
    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.primaryVariant),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Scaffold Example Content",
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center
        )
    }
}