package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

data class MenuItem(val name: String, val icon: ImageVector? = null, val targetScreen: Screen)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainMenuScreen(
    menuItems: List<MenuItem> = emptyList(),
    onToggleTheme: () -> Unit = {},
    onToggleFont: () -> Unit = {},
    onMenuItemSelected: (Screen) -> Unit = {}
) {
    Box {
        Image(
            painter = painterResource("images/compose-desktop-logo.png"),
            contentDescription = "Compose for Desktop logo",
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(0.4f),
        )

        Column(

        ) {
            Spacer(Modifier.height(8.dp))

            LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 400.dp)) {
                items(menuItems) { menuItem ->
                    MenuItemCard(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(0.2f)
                            .height(96.dp),
                        text = menuItem.name,
                        icon = menuItem.icon
                    ) { onMenuItemSelected(menuItem.targetScreen) }
                }
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MenuItemCard(
                    modifier = Modifier.height(64.dp).clip(RoundedCornerShape(16.dp)),
                    text = "CTRL + T to toggle theme",
                    backgroundColor = MaterialTheme.colors.secondary,
                    onItemSelected = onToggleTheme
                )

                MenuItemCard(
                    modifier = Modifier.height(64.dp).clip(RoundedCornerShape(16.dp)),
                    text = "CTRL + F to toggle font",
                    backgroundColor = MaterialTheme.colors.secondary,
                    onItemSelected = onToggleFont
                )
            }
        }
    }
}

@Composable
private fun MenuItemCard(
    modifier: Modifier = Modifier,
    text: String,
    icon: ImageVector? = null,
    backgroundColor: Color = MaterialTheme.colors.primary.copy(alpha = 0.6f),
    onItemSelected: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onItemSelected() },
        backgroundColor = backgroundColor,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            icon?.let {
                Icon(
                    it, null,
                    modifier = Modifier.size(64.dp).padding(end = 16.dp)
                )
            }

            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4,
            )
        }
    }
}
