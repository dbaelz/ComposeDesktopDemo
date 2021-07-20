package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random


@Composable
fun CustomLayoutScreen(onBackNavigation: () -> Unit) {
    MenuColumn(onBackNavigation, listOf(
        { TextWithCustomModifier() },
        { TextCustomLayoutExample("Hello there!") }
    ))

}

@Composable
fun TextWithCustomModifier() {
    Text(
        "Think outside the box",
        modifier = Modifier
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

@Composable
private fun TextCustomLayoutExample(text: String) {
    CustomLayout {
        (0..9).forEach {
            Text(
                text,
                modifier = Modifier
                    .width(128.dp)
                    .height(64.dp)
                    .background(
                        Color(
                            Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)
                        )
                    )
            )
        }
    }


}

@Composable
private fun CustomLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeables = measurables.map {
            it.measure(constraints)
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            var xPos = 0
            var yPos = 0

            placeables.forEach {
                it.place(xPos, yPos)

                xPos += it.width / 2
                yPos += it.height / 2
            }
        }
    }
}