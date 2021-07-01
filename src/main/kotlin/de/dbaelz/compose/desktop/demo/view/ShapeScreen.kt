package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ShapeScreen(onBackNavigation: () -> Unit) {
    val items = listOf<@Composable () -> Unit>(
        { TextComponent(shape = AbsoluteCutCornerShape(8.dp)) },
        {
            TextComponent(
                shape = AbsoluteCutCornerShape(
                    topLeftPercent = 50,
                    bottomLeftPercent = 50
                )
            )
        },
        { TextComponent(shape = AbsoluteCutCornerShape(percent = 50)) },
        { TextComponent(shape = AbsoluteCutCornerShape(topLeftPercent = 100)) },

        { Spacer(modifier = Modifier.height(16.dp)) },

        { TextComponent(shape = CutCornerShape(8.dp)) },
        { TextComponent(shape = CutCornerShape(topStart = 20.dp, topEnd = 20.dp)) },
        { TextComponent(shape = CutCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)) },

        { TextComponent(shape = CutCornerShape(topStartPercent = 50, topEndPercent = 50)) },
        { TextComponent(shape = CutCornerShape(bottomStartPercent = 50, bottomEndPercent = 50)) },
        { TextComponent(shape = CutCornerShape(topStartPercent = 100)) },

        { Spacer(modifier = Modifier.height(16.dp)) },

        { TextComponent(shape = AbsoluteRoundedCornerShape(8.dp)) },
        { TextComponent(shape = RoundedCornerShape(8.dp)) },
        { TextComponent(shape = RoundedCornerShape(percent = 50)) },
        {
            TextComponent(
                width = 64.dp,
                height = 64.dp,
                shape = RoundedCornerShape(
                    topStartPercent = 50,
                    topEndPercent = 50,
                    bottomStartPercent = 50,
                    bottomEndPercent = 10
                ),
                text = "42"
            )
        },

        { Spacer(modifier = Modifier.height(16.dp)) },

        { TextComponent(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) },

        {
            TextComponent(
                shape = AbsoluteCutCornerShape(
                    topLeftPercent = 50,
                    bottomLeftPercent = 50
                )
            )
        },
        {
            TextComponent(
                shape = AbsoluteCutCornerShape(
                    topLeftPercent = 50,
                    bottomLeftPercent = 50
                )
            )
        },

        { Spacer(modifier = Modifier.height(16.dp)) },

        { Diamond(45f) },
        { Diamond(20f) },
        { Diamond(-20f) },
    )

    MenuColumn(onBackNavigation, items)
}

@Composable
private fun TextComponent(
    width: Dp = 200.dp,
    height: Dp = 40.dp,
    shape: Shape,
    text: String = "Shape Test"
) {
    Surface(
        shape = shape,
        color = MaterialTheme.colors.secondary,
        elevation = 8.dp,
        modifier = Modifier
            .width(width)
            .height(height)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
private fun Diamond(rotation: Float) {
    Box(Modifier.rotate(rotation)) {
        Surface(
            shape = AbsoluteCutCornerShape(topLeftPercent = 50),
            border = BorderStroke(4.dp, MaterialTheme.colors.primary)
        ) {
            Box(
                modifier = Modifier
                    .border(4.dp, MaterialTheme.colors.primary)
                    .background(MaterialTheme.colors.secondary)
                    .size(64.dp)
            )
        }
    }
}