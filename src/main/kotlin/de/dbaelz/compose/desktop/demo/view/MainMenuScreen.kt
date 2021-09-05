package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

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
            painter = painterResource("images/compose-logo.png"),
            contentDescription = "Compose for Desktop logo",
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(0.4f),
        )

        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
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

                model.items.forEach {
                    when (it) {
                        is MainMenuModel.Item.Separator -> {
                            Spacer(Modifier.height(16.dp))
                        }
                        is MainMenuModel.Item.Entry -> {
                            MenuButton(
                                Modifier.align(Alignment.CenterHorizontally),
                                it,
                                onItemSelected
                            )

                            Spacer(Modifier.height(8.dp))
                        }
                    }
                }
            }

            VerticalScrollbar(
                adapter = rememberScrollbarAdapter(scrollState),
                modifier = Modifier.width(16.dp)
            )
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
