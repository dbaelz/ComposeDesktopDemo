package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import de.dbaelz.compose.desktop.demo.Screen

data class MainMenuModel(val items: List<Item> = emptyList()) {
    sealed class Item {
        object Separator : Item()
        data class Entry(
            val name: String,
            val icon: ImageVector? = null,
            val targetScreen: Screen,
        ) : Item()
    }
}

@Composable
fun MainMenuScreen(model: MainMenuModel = MainMenuModel(), onItemSelected: (Screen) -> Unit) {
    val scrollState = rememberScrollState(0)

    Box {
        Image(
            bitmap = useResource("images/compose-logo.png") { loadImageBitmap(it) },
            contentDescription = "Compose for Desktop logo",
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(0.4f),
        )

        Column(
            modifier = Modifier.align(Alignment.Center)
                .fillMaxWidth()
                .verticalScroll(scrollState),
        ) {
            Spacer(Modifier.height(8.dp))

            Text(
                text = "Press CTRL + ALT + D to toggle theme",
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(12.dp))

            model.items.forEach {
                when (it) {
                    is MainMenuModel.Item.Separator -> {
                        Spacer(Modifier.height(16.dp))
                    }
                    is MainMenuModel.Item.Entry -> {
                        MenuButton(Modifier.align(Alignment.CenterHorizontally), it, onItemSelected)

                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }

    }
}

@Composable
private fun MenuButton(
    modifier: Modifier,
    entry: MainMenuModel.Item.Entry,
    onItemSelected: (Screen) -> Unit
) {
    Button(
        modifier = modifier.requiredWidth(200.dp).height(36.dp),
        onClick = { onItemSelected(entry.targetScreen) }
    ) {
        entry.icon?.let {
            Icon(
                it, null,
                modifier = Modifier.size(28.dp).padding(end = 8.dp)
            )
        }
        Text(entry.name)
    }
}
