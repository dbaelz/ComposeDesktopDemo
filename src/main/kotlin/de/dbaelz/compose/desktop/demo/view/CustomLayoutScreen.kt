package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random


@Composable
fun CustomLayoutScreen(onBackNavigation: () -> Unit) {
    Screen(
        { ScreenTopBar("Custom Layouts", onBackNavigation) },
        listOf(
            { RowWithChildsWidthIsWidestTextWidth() },
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
        repeat((0..9).count()) {
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

/*
 * In this layout all Box/Text inside the row are as long as the widest text.
 * Not a custom layout, but shows how the provided modifier + IntrinsicSize can be used
 * to get an "unusual" layout requirement done.
 */
@Composable
private fun RowWithChildsWidthIsWidestTextWidth() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.width(IntrinsicSize.Max).fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BoxWithText(
                modifier = Modifier.weight(1f),
                "Short text",
                MaterialTheme.colors.primary
            )

            Spacer(Modifier.width(16.dp))

            BoxWithText(
                modifier = Modifier.weight(1f),
                "text",
                MaterialTheme.colors.secondaryVariant
            )

            Spacer(Modifier.width(16.dp))

            BoxWithText(
                modifier = Modifier.weight(1f),
                "Very very very long text",
                MaterialTheme.colors.secondary
            )

            Spacer(Modifier.width(16.dp))

            BoxWithText(
                modifier = Modifier.weight(1f),
                "Another text",
                MaterialTheme.colors.primaryVariant
            )
        }
    }
}

@Composable
private fun BoxWithText(modifier: Modifier, text: String, backgroundColor: Color) {
    Box(
        modifier = modifier.fillMaxHeight().background(backgroundColor)
    ) {
        Text(text)
    }
}