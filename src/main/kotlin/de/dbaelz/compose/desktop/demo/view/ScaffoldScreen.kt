package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
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
        drawerBackgroundColor = MaterialTheme.colors.background.copy(alpha = 0.90f),
        content = { Content(onBackNavigation) }
    )
}

@Composable
private fun TopBar(onMenuIconClicked: () -> Unit) {
    TopAppBar {
        Spacer(Modifier.width(8.dp))

        Icon(Icons.Default.Menu, null,
            modifier = Modifier.clickable {
                onMenuIconClicked()
            })

        Spacer(Modifier.width(8.dp))

        Text("Scaffold TopBar")
    }
}

@Composable
private fun Drawer() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.height(96.dp).padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                bitmap = imageFromResource("images/compose-logo.png"),
                contentDescription = "Compose for Desktop logo"
            )

            Text(
                text = "Scaffold Drawer",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Divider(
            thickness = 2.dp,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
        )

        val maxItems = 5
        (1..maxItems).forEach {
            DrawerEntry(Icons.Default.Home, "Entry $it")

            if (it != maxItems) Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
        }
    }
}

@Composable
private fun DrawerEntry(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier.height(64.dp).padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            modifier = Modifier.size(42.dp),
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colors.primaryVariant
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.h6,
        )
    }
}

@Composable
private fun Content(onBackNavigation: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Scaffold Example Content",
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center
        )

        Button(
            modifier = Modifier.width(248.dp),
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