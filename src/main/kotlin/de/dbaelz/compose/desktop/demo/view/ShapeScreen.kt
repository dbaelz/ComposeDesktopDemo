package de.dbaelz.compose.desktop.demo.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ShapeScreen(onBackNavigation: () -> Unit) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var selectedTabType by remember { mutableStateOf(ShapeTabType.ABSOLUTE_CUT) }

    val tabs = listOf(
        Tab(ShapeTabType.ABSOLUTE_CUT, "Absolute Cut"),
        Tab(ShapeTabType.CUT_CORNER, "Cut Corner"),
        Tab(ShapeTabType.ROUNDED, "Rounded"),
        Tab(ShapeTabType.COMBINATION, "Combination"),
        Tab(ShapeTabType.DIAMOND, "Diamond"),
        Tab(ShapeTabType.SPECIAL, "Special"),
    )

    Column {
        ScreenTopBar("Shapes", onBackNavigation)

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
            ShapeTabType.ABSOLUTE_CUT -> AbsoluteCutTab()
            ShapeTabType.CUT_CORNER -> CutCornerTab()
            ShapeTabType.ROUNDED -> RoundedCornerTab()
            ShapeTabType.COMBINATION -> CombinationTab()
            ShapeTabType.DIAMOND -> DiamondTab()
            ShapeTabType.SPECIAL -> SpecialTab()
        }
    }
}

@Composable
private fun AbsoluteCutTab() {
    ScreenItemsColumn(
        listOf(
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
private fun CutCornerTab() {
    ScreenItemsColumn(
        listOf(
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
private fun RoundedCornerTab() {
    ScreenItemsColumn(
        listOf(
            { TextComponent(shape = AbsoluteRoundedCornerShape(8.dp)) },
            { TextComponent(shape = RoundedCornerShape(8.dp)) },
            { TextComponent(shape = RoundedCornerShape(percent = 50)) },
        )
    )
}

@Composable
private fun CombinationTab() {
    ScreenItemsColumn(
        listOf(
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
private fun DiamondTab() {
    ScreenItemsColumn(
        listOf(
            { Diamond() },
            { Diamond(20f) },
            { Diamond(-20f) },
            { DiamondWithLinearSlider() },
        )
    )
}

@Composable
private fun SpecialTab() {
    val heartUnselectedState =
        ShapeState("", MaterialTheme.colors.primary, MaterialTheme.colors.onPrimary)
    val heartSelectedState = ShapeState("+1", Color.Red, Color.White)

    var tearDropText by remember { mutableStateOf(1) }
    var plusOneHeartState by remember { mutableStateOf(heartUnselectedState) }


    var kotlinShapeClickable by remember { mutableStateOf(true) }
    var kotlinShapeIsRotated by remember { mutableStateOf(false) }
    val kotlinShapeRotation: Float by animateFloatAsState(
        targetValue = if (kotlinShapeIsRotated) 360F else 0F,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        finishedListener = { kotlinShapeClickable = true }
    )

    var isClicked by remember { mutableStateOf(false) }

    ScreenItemsColumn(
        listOf(
            {
                TearDrop(text = tearDropText.toString(), onClicked = { tearDropText++ })
            },
            {
                AnimatedIcon(
                    icon = Icons.Outlined.FavoriteBorder,
                    modifier = Modifier.size(54.dp),
                    state = AnimatedIconState(
                        if (isClicked) 1.5f else 1f,
                        if (isClicked) MaterialTheme.colors.primary else Color.LightGray
                    ),
                    onClick = { isClicked = !isClicked }
                )
            },
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
            { Arrowhead() },
            {
                KotlinShape(rotation = kotlinShapeRotation, onClicked = {
                    if (kotlinShapeClickable) {
                        kotlinShapeClickable = false
                        kotlinShapeIsRotated = !kotlinShapeIsRotated
                    }
                })
            },
            {
                ShapeShiftingButton()
            },
        )
    )
}

@Composable
private fun TextComponent(
    modifier: Modifier = Modifier,
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
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
private fun DiamondWithLinearSlider(initialRotation: Float = 45f) {
    var sliderPosition by remember { mutableStateOf(0f) }

    Diamond(sliderPosition + initialRotation)

    Spacer(Modifier.height(8.dp))

    Slider(
        value = sliderPosition,
        valueRange = 0f..360f,
        onValueChange = { sliderPosition = it },
        modifier = Modifier.width(128.dp)
    )
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
private fun TearDrop(text: String = "", size: Dp = 64.dp, onClicked: (() -> Unit)? = null) {
    val click = if (onClicked != null) {
        Modifier.clickable { onClicked() }
    } else Modifier

    TextComponent(
        modifier = click,
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

@Composable
private fun KotlinShape(
    radialGradient: RadialGradient = RadialGradient(
        colorStops = listOf(
            3.435144e-03f to Color(0xFFE44857),
            0.4689f to Color(0xFFFC711E1),
            1f to Color(0xFF7F52FF)
        ),
        radius = 86.7174f,
        center = Offset(67.8027f, 3.9181f)
    ),
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
            .clip(KotlinShape)
            .background(
                // Values based on the SVG provided on the website
                Brush.radialGradient(
                    colorStops = radialGradient.colorStops.toTypedArray(),
                    center = radialGradient.center,
                    radius = radialGradient.radius
                )
            ).then(click),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = textColor)
    }
}

private val KotlinShape = GenericShape { size, _ ->
    lineTo(size.width, 0f)
    lineTo(size.width / 2, size.height / 2)
    lineTo(size.width, size.height)
    lineTo(0f, size.height)
    close()
}


@Composable
private fun ShapeShiftingButton(
    width: Dp = 60.dp,
    widthExpanded: Dp = 200.dp,
    height: Dp = 60.dp,
    heightExpanded: Dp = 60.dp,
    text: String = "Click!",
    icon: ImageVector = Icons.Default.Menu
) {
    var isExpanded by remember { mutableStateOf(false) }

    val animationDuration = 500
    val transition = updateTransition(isExpanded)
    val animatedWidth by transition.animateDp(
        transitionSpec = { tween(durationMillis = animationDuration, easing = LinearEasing) },
        targetValueByState = { if (it) widthExpanded else width }
    )
    val animatedHeight by transition.animateDp(
        transitionSpec = { tween(durationMillis = animationDuration, easing = LinearEasing) },
        targetValueByState = { if (it) heightExpanded else height }
    )
    val textAnimation by transition.animateFloat(
        transitionSpec = { tween(durationMillis = animationDuration) },
        targetValueByState = { if (it) 1f else 0f }
    )
    val cornerPercentage = if (isExpanded) 20 else 50

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .animateContentSize()
            .width(animatedWidth)
            .height(animatedHeight)
            .clip(
                RoundedCornerShape(
                    topStartPercent = cornerPercentage,
                    topEndPercent = cornerPercentage,
                    bottomStartPercent = cornerPercentage,
                    bottomEndPercent = cornerPercentage
                )
            )
            .background(Color.Green)
            .clickable { isExpanded = !isExpanded }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (textAnimation >= 0.5f) {
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.alpha(textAnimation)
                )
            } else {
                Icon(icon, null, modifier = Modifier.alpha(1 - textAnimation))
            }
        }
    }
}

@Composable
private fun AnimatedIcon(
    icon: ImageVector = Icons.Outlined.Home,
    modifier: Modifier = Modifier,
    state: AnimatedIconState,
    onClick: () -> Unit = {}
) {
    val animatedScale: Float by animateFloatAsState(targetValue = state.scale)
    val animatedColor by animateColorAsState(targetValue = state.color)

    Icon(
        imageVector = icon,
        contentDescription = "animated ${icon.name} icon that changes its size and color on click",
        tint = animatedColor,
        modifier = modifier
            .scale(animatedScale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onClick() }
            )
    )
}

private data class AnimatedIconState(val scale: Float, val color: Color)

private data class ShapeState(val text: String, val itemColor: Color, val textColor: Color)

private data class RadialGradient(
    val colorStops: List<Pair<Float, Color>>,
    val center: Offset,
    val radius: Float
)

private enum class ShapeTabType {
    ABSOLUTE_CUT,
    CUT_CORNER,
    ROUNDED,
    COMBINATION,
    DIAMOND,
    SPECIAL
}