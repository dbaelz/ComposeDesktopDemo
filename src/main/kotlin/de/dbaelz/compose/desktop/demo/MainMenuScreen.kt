package de.dbaelz.compose.desktop.demo

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.unit.dp

@Composable
fun MainMenuScreen(model: MainMenuModel = MainMenuModel()) {
    val scrollState = rememberScrollState(0)

    Box(
        Modifier
            .padding(4.dp)
            .border(BorderStroke(4.dp, MaterialTheme.colors.primary))
            .fillMaxSize()
    ) {
        Image(
            bitmap = imageFromResource("compose-logo.png"),
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
            model.entries.forEach {
                Button(
                    modifier = Modifier
                        .requiredWidth(150.dp)
                        .align(Alignment.CenterHorizontally),
                    onClick = it.onSelectedAction
                ) {
                    Text(it.name)
                }

                Spacer(Modifier.height(8.dp))
            }

            Spacer(Modifier.height(32.dp))
        }

    }
}

data class MainMenuModel(val entries: List<Entry> = emptyList()) {
    data class Entry(val name: String, val onSelectedAction: () -> Unit)
}