package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ShapeScreen(onBackNavigation: () -> Unit) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var selectedTabType by remember { mutableStateOf(ShapeTabType.ABSOLUTE_CUT) }

    val tabs = listOf(
        Tab(ShapeTabType.ABSOLUTE_CUT, "Absolute Cut", null),
        Tab(ShapeTabType.CUT_CORNER, "Cut Corner", null),
        Tab(ShapeTabType.ROUNDED, "Rounded", null),
        Tab(ShapeTabType.COMBINATION, "Combination", null),
        Tab(ShapeTabType.DIAMOND, "Diamond", null),
        Tab(ShapeTabType.SPECIAL, "Special", null),
    )

    Column {
        TabRowComponent(
            tabs = tabs,
            selectedTabIndex = selectedTabIndex,
            tabStyle = TabStyle(withText = true, withIcon = false),
            onTabSelected = { index, type ->
                selectedTabIndex = index
                selectedTabType = type
            }
        )

        when (selectedTabType) {
            ShapeTabType.ABSOLUTE_CUT -> AbsoluteCutTab(onBackNavigation)
            ShapeTabType.CUT_CORNER -> CutCornerTab(onBackNavigation)
            ShapeTabType.ROUNDED -> RoundedCornerTab(onBackNavigation)
            ShapeTabType.COMBINATION -> CombinationTab(onBackNavigation)
            ShapeTabType.DIAMOND -> DiamondTab(onBackNavigation)
            ShapeTabType.SPECIAL -> SpecialTab(onBackNavigation)
        }
    }
}

@Composable
private fun AbsoluteCutTab(onBackNavigation: () -> Unit) {
    MenuColumn(
        onBackNavigation, listOf(
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
        )
    )
}

@Composable
private fun CutCornerTab(onBackNavigation: () -> Unit) {
    MenuColumn(
        onBackNavigation, listOf(
            { TextComponent(shape = CutCornerShape(8.dp)) },
            { TextComponent(shape = CutCornerShape(topStart = 20.dp, topEnd = 20.dp)) },
            { TextComponent(shape = CutCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)) },

            { TextComponent(shape = CutCornerShape(topStartPercent = 50, topEndPercent = 50)) },
            {
                TextComponent(
                    shape = CutCornerShape(
                        bottomStartPercent = 50,
                        bottomEndPercent = 50
                    )
                )
            },
            { TextComponent(shape = CutCornerShape(topStartPercent = 100)) },
        )
    )
}

@Composable
private fun RoundedCornerTab(onBackNavigation: () -> Unit) {
    MenuColumn(
        onBackNavigation, listOf(
            { TextComponent(shape = AbsoluteRoundedCornerShape(8.dp)) },
            { TextComponent(shape = RoundedCornerShape(8.dp)) },
            { TextComponent(shape = RoundedCornerShape(percent = 50)) },
        )
    )
}

@Composable
private fun CombinationTab(onBackNavigation: () -> Unit) {
    MenuColumn(
        onBackNavigation, listOf(
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
        )
    )
}

@Composable
private fun DiamondTab(onBackNavigation: () -> Unit) {
    MenuColumn(
        onBackNavigation, listOf(
            { Diamond() },
            { Diamond(20f) },
            { Diamond(-20f) },
        )
    )
}

@Composable
private fun SpecialTab(onBackNavigation: () -> Unit) {
    val heartUnselectedState =
        ShapeState("", MaterialTheme.colors.primary, MaterialTheme.colors.onPrimary)
    val heartSelectedState = ShapeState("+1", Color.Red, Color.White)

    var plusOneHeartState by remember { mutableStateOf(heartUnselectedState) }

    MenuColumn(
        onBackNavigation, listOf(
            { TearDrop("42") },
            { Heart() },
            {
                Heart(
                    color = MaterialTheme.colors.primary,
                    textColor = MaterialTheme.colors.onPrimary,
                    text = "Text"
                )
            },
            {
                Heart(
                    color = plusOneHeartState.itemColor,
                    textColor = plusOneHeartState.textColor,
                    text = plusOneHeartState.text,
                    onClicked = {
                        plusOneHeartState = if (plusOneHeartState == heartUnselectedState) {
                            heartSelectedState
                        } else {
                            heartUnselectedState
                        }
                    }
                )
            },
            { Arrowhead() }
        )
    )
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
private fun Diamond(rotation: Float = 45f) {
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

@Composable
private fun TearDrop(text: String = "", size: Dp = 64.dp) {
    TextComponent(
        width = size,
        height = size,
        shape = RoundedCornerShape(
            topStartPercent = 50,
            topEndPercent = 50,
            bottomStartPercent = 50,
            bottomEndPercent = 10
        ),
        text = text
    )
}

@Composable
private fun Heart(
    color: Color = Color.Red,
    textColor: Color = Color.White,
    text: String = "",
    rotation: Float = 0f,
    onClicked: (() -> Unit)? = null
) {
    val click = if (onClicked != null) {
        Modifier.clickable { onClicked() }
    } else Modifier

    Box(
        modifier = Modifier
            .size(96.dp)
            .rotate(rotation)
            .clip(HeartShape)
            .background(color)
            .then(click),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = textColor)
    }
}

private val HeartShape = GenericShape { size, _ ->
    lineTo(0.5f * size.width, 0.25f * size.height)
    cubicTo(
        0.5f * size.width,
        0.225f * size.height,
        0.458f * size.width,
        0.125f * size.height,
        0.2916f * size.width,
        0.125f * size.height
    )
    cubicTo(
        0.0416f * size.width,
        0.125f * size.height,
        0.0416f * size.width,
        0.4f * size.height,
        0.0416f * size.width,
        0.4f * size.height
    )
    cubicTo(
        0.0416f * size.width,
        0.583f * size.height,
        0.208f * size.width,
        0.77f * size.height,
        0.5f * size.width,
        0.917f * size.height
    )
    cubicTo(
        0.791f * size.width,
        0.77f * size.height,
        0.958f * size.width,
        0.583f * size.height,
        0.958f * size.width,
        0.4f * size.height
    )
    cubicTo(
        0.958f * size.width,
        0.4f * size.height,
        0.958f * size.width,
        0.125f * size.height,
        0.708f * size.width,
        0.125f * size.height
    )
    cubicTo(
        0.583f * size.width,
        0.125f * size.height,
        0.5f * size.width,
        0.225f * size.height,
        0.5f * size.width,
        0.25f * size.height
    )
    close()
}

@Composable
private fun Arrowhead(
    color: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.onPrimary,
    text: String = "",
    rotation: Float = 0f
) {
    Box(
        modifier = Modifier
            .size(96.dp)
            .rotate(rotation)
            .clip(ArrowheadShape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = textColor)
    }
}

private val ArrowheadShape = GenericShape { size, _ ->
    moveTo(size.width / 2f, 0f)
    lineTo(size.width, size.height)
    quadraticBezierTo(
        size.width * 0.5f,
        size.height * 0.6f,
        0f,
        size.height
    )
    close()
}

private data class ShapeState(val text: String, val itemColor: Color, val textColor: Color)

private enum class ShapeTabType {
    ABSOLUTE_CUT,
    CUT_CORNER,
    ROUNDED,
    COMBINATION,
    DIAMOND,
    SPECIAL
}