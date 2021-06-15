package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.unit.dp

@Composable
fun MainMenuScreen(model: MainMenuModel = MainMenuModel()) {
    val scrollState = rememberScrollState(0)

    Box(
        Modifier
            .border(
                width = 16.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colors.secondary,
                        MaterialTheme.colors.primary
                    )
                ),
                shape = RectangleShape
            )
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
            Spacer(Modifier.height(32.dp))

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