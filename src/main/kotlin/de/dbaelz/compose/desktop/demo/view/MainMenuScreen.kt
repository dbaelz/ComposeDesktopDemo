package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

data class MenuItem(val name: String, val icon: ImageVector? = null, val targetScreen: Screen)

@ExperimentalFoundationApi
@Composable
fun MainMenuScreen(menuItems: List<MenuItem> = emptyList(), onItemSelected: (Screen) -> Unit) {
    Box {
        Image(
            painter = painterResource("images/compose-desktop-logo.png"),
            contentDescription = "Compose for Desktop logo",
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(0.4f),
        )

        Row {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Spacer(Modifier.height(8.dp))

                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(
                        text = "Press CTRL + T to toggle theme",
                        color = MaterialTheme.colors.onBackground,
                    )

                    Text("|", modifier = Modifier.padding(horizontal = 16.dp))

                    Text(
                        text = "Press CTRL + F to toggle font",
                        color = MaterialTheme.colors.onBackground,
                    )
                }


                Spacer(Modifier.height(12.dp))

                LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 400.dp)) {
                    items(menuItems) { menuItem ->
                        MenuItemCard(menuItem) { onItemSelected(menuItem.targetScreen) }
                    }
                }
            }
        }
    }
}

@Composable
private fun MenuItemCard(
    menuItem: MenuItem,
    onItemSelected: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(0.2f)
            .height(96.dp)
            .clickable { onItemSelected() },
        backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.6f),
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            menuItem.icon?.let {
                Icon(
                    it, null,
                    modifier = Modifier.size(64.dp).padding(end = 16.dp)
                )
            }

            Text(
                text = menuItem.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4,
            )
        }
    }
}
