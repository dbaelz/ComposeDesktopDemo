package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

private data class DrawerItem(val icon: ImageVector, val name: String)

private const val INITIAL_CONTENT_TEXT = "Scaffold Example"

@Composable
fun ScaffoldScreen(onBackNavigation: () -> Unit) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var contentText by remember { mutableStateOf(INITIAL_CONTENT_TEXT) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        },
        bottomBar = { BottomBar() },
        floatingActionButton = { Fab { contentText = INITIAL_CONTENT_TEXT } },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        drawerContent = {
            Drawer(drawerItems) {
                contentText = it.name
                coroutineScope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        },
        drawerBackgroundColor = MaterialTheme.colors.background.copy(alpha = 0.90f),
        content = { Content(contentText, onBackNavigation) }
    )
}

// Usually handed over as model to the screen
private val drawerItems = listOf(
    DrawerItem(Icons.Default.Home, "Home"),
    DrawerItem(Icons.Default.Favorite, "Favorites"),
    DrawerItem(Icons.Default.AddCircle, "Add"),
    DrawerItem(Icons.Default.Info, "Info"),
)

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
private fun BottomBar() {
    BottomAppBar {
        Box(modifier = Modifier.height(64.dp))
    }
}

@Composable
private fun Fab(onFabClicked: () -> Unit) {
    FloatingActionButton(onClick = onFabClicked) {
        Icon(Icons.Default.Clear, null)
    }
}

@Composable
private fun Drawer(drawerItems: List<DrawerItem>, onItemSelected: (DrawerItem) -> Unit) {
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

        drawerItems.forEachIndexed { index, drawerItem ->
            DrawerEntry(drawerItem) {
                onItemSelected(it)
            }

            if (index != drawerItems.lastIndex) {
                Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
            }
        }
    }
}

@Composable
private fun DrawerEntry(item: DrawerItem, onItemSelected: (DrawerItem) -> Unit = {}) {
    Row(
        modifier = Modifier.height(64.dp).fillMaxWidth()
            .clickable { onItemSelected(item) }
            .padding(4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            modifier = Modifier.size(36.dp),
            imageVector = item.icon,
            contentDescription = null,
            tint = MaterialTheme.colors.primaryVariant
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = item.name,
            style = MaterialTheme.typography.h6,
        )
    }
}

@Composable
private fun Content(contentText: String, onBackNavigation: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = contentText,
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