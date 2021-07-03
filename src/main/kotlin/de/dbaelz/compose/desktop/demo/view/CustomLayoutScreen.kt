package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CustomLayoutScreen(onBackNavigation: () -> Unit) {
    MenuColumn(onBackNavigation, listOf({
        TextWithCustomModifier()
    }))

}

@Composable
fun TextWithCustomModifier() {
    Text(
        "Layout Modifier", Modifier
            .background(Color.Red)
            .width(140.dp)
            .height(40.dp)
            .offsetX(100.dp)
    )
}

// Not really practical but ok as example
private fun Modifier.offsetX(offset: Dp) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)

    layout(placeable.width, placeable.height) {
        placeable.place(offset.roundToPx(), 0)
    }
}