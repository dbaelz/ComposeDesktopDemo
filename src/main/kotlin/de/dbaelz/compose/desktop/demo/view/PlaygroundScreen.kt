package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PlaygroundScreen(onBackNavigation: () -> Unit) {
    val items = listOf<@Composable () -> Unit>(
        { TextComponent(AbsoluteCutCornerShape(8.dp)) },
        { TextComponent(CutCornerShape(8.dp)) },
        { TextComponent(AbsoluteRoundedCornerShape(8.dp)) },
        { TextComponent(RoundedCornerShape(8.dp)) }
    )

    MenuColumn(onBackNavigation, items)
}

@Composable
fun TextComponent(shape: Shape) {
    Surface(
        shape = shape,
        color = MaterialTheme.colors.primary,
        elevation = 8.dp,
        modifier = Modifier
            .width(200.dp)
            .height(40.dp)
    ) {
        Text(
            text = "Shape Test",
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )
    }
}