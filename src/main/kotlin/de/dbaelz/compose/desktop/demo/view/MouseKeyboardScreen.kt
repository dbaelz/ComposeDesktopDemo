package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@ExperimentalFoundationApi
@Composable
fun MouseKeyboardScreen(onBackNavigation: () -> Unit) {
    var lastClickLabel by remember { mutableStateOf("") }

    MenuColumn(
        onBackNavigation, listOf {
            MouseClick(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { lastClickLabel = "Click" },
                onLongClick = { lastClickLabel = "LongClick" },
                onDoubleClick = { lastClickLabel = "DoubleClick" })

            Spacer(Modifier.height(8.dp))

            Text(text = lastClickLabel, style = MaterialTheme.typography.h3)
        }
    )
}

@ExperimentalFoundationApi
@Composable
private fun MouseClick(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    onDoubleClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(200.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colors.primary)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
                onDoubleClick = onDoubleClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Click me")
    }
}
