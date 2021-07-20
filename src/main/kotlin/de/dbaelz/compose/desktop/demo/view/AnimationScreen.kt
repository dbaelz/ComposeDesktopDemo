package de.dbaelz.compose.desktop.demo.view

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@ExperimentalAnimationApi
@Composable
fun AnimationScreen(onBackNavigation: () -> Unit) {
    var backgroundColorToggle by remember { mutableStateOf(true) }
    val backgroundColor by animateColorAsState(if (backgroundColorToggle) colors.secondary else colors.secondaryVariant)


    var boxState by remember { mutableStateOf(BoxState.NORMAL) }
    val transition = updateTransition(targetState = boxState)
    val boxColor by transition.animateColor { state ->
        if (state == BoxState.NORMAL) colors.primaryVariant else colors.primary
    }
    val boxHeight by transition.animateDp(
        transitionSpec = {
            if (targetState == BoxState.NORMAL) spring() else spring(stiffness = Spring.StiffnessVeryLow)
        }
    ) { state ->
        if (state == BoxState.NORMAL) 32.dp else 128.dp
    }
    var boxVisible by remember { mutableStateOf(true) }

    var backButtonClicked by remember { mutableStateOf(false) }
    val backButtonOffset by animateDpAsState(
        targetValue = if (backButtonClicked) (-45).dp else 0.dp,
        animationSpec = spring(stiffness = Spring.StiffnessLow)
    ) {
        onBackNavigation()
    }

    AnimationContent(
        backgroundColor = backgroundColor,
        backButtonOffset = backButtonOffset,
        animationBoxColor = boxColor,
        animationBoxHeight = boxHeight,
        isAnimationBoxVisible = boxVisible,
        isAnimationBoxExpanded = boxState != BoxState.NORMAL,
        isEnabled = !backButtonClicked,
        onBackgroundToggleClicked = {
            backgroundColorToggle = !backgroundColorToggle
        },
        onAnimateBoxClicked = {
            boxState = when (boxState) {
                BoxState.NORMAL -> BoxState.LARGE
                BoxState.LARGE -> BoxState.NORMAL
            }
        },
        onBoxVisibilityClicked = {
            boxVisible = !boxVisible
        },
        onBackButtonClicked = {
            backButtonClicked = true
        }
    )
}

@ExperimentalAnimationApi
@Composable
private fun AnimationContent(
    backgroundColor: Color,
    backButtonOffset: Dp,
    animationBoxColor: Color,
    animationBoxHeight: Dp,
    isAnimationBoxVisible: Boolean,
    isAnimationBoxExpanded: Boolean,
    isEnabled: Boolean,
    onBackgroundToggleClicked: () -> Unit,
    onAnimateBoxClicked: () -> Unit,
    onBoxVisibilityClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(backgroundColor, colors.background), 0.01f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onBackButtonClicked,
            enabled = isEnabled,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = backButtonOffset)
        ) {
            Text("Back to menu")
        }

        Spacer(modifier = Modifier.height(32.dp))

        DotsLoadingIndicator(
            numberDots = 4,
            animateSize = true,
            colorDot = colors.primary,
            colorHighlight = colors.secondaryVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        AnimationButton(
            "Toggle background",
            isEnabled,
            Modifier.align(Alignment.CenterHorizontally),
            onBackgroundToggleClicked
        )

        Spacer(modifier = Modifier.height(8.dp))

        AnimationButton(
            "Animate Box",
            isEnabled,
            Modifier.align(Alignment.CenterHorizontally),
            onAnimateBoxClicked
        )

        Spacer(modifier = Modifier.height(8.dp))

        AnimationButton(
            "Box Visibility",
            isEnabled,
            Modifier.align(Alignment.CenterHorizontally),
            onBoxVisibilityClicked
        )

        Spacer(modifier = Modifier.height(8.dp))

        AnimatedBox(
            Modifier.align(Alignment.CenterHorizontally),
            isAnimationBoxVisible && isEnabled,
            animationBoxColor,
            animationBoxHeight,
            isAnimationBoxExpanded
        )
    }
}

@Composable
private fun AnimationButton(
    text: String,
    enabled: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text)
    }
}

@ExperimentalAnimationApi
@Composable
private fun AnimatedBox(
    modifier: Modifier,
    boxVisible: Boolean,
    boxColor: Color,
    boxHeight: Dp,
    expanded: Boolean
) {
    AnimatedVisibility(
        visible = boxVisible,
        enter = fadeIn() + expandIn(animationSpec = boxAnimationSpec),
        exit = shrinkOut(animationSpec = boxAnimationSpec) + fadeOut(),
        modifier = modifier
    ) {
        Box(
            modifier = modifier.background(boxColor)
                .animateContentSize()
                .height(boxHeight)
                .width(132.dp)
        ) {
            Text(
                "Hello there! This is a wonderful text example.",
                color = colors.onPrimary,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                maxLines = if (expanded) Int.MAX_VALUE else 1,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
private fun DotsLoadingIndicator(
    numberDots: Int = 3,
    millisPerDot: Int = 500,
    animateSize: Boolean = false,
    colorDot: Color = colors.primary,
    colorHighlight: Color = colors.secondary,
) {
    assert(numberDots > 0)
    assert(millisPerDot > 0)

    val infiniteTransition = rememberInfiniteTransition()
    val dotsAnimation by infiniteTransition.animateValue(
        0,
        numberDots,
        Int.VectorConverter,
        infiniteRepeatable(
            animation = keyframes {
                durationMillis = numberDots * millisPerDot
                (0 until numberDots).forEach {
                    it at it * millisPerDot
                }
            }
        )
    )

    Row {
        (0 until numberDots).forEach {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(if (dotsAnimation == it && animateSize) 24.dp else 16.dp)
                    .clip(CircleShape)
                    .background(if (dotsAnimation == it) colorHighlight else colorDot)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

private val boxAnimationSpec: FiniteAnimationSpec<IntSize> = spring(stiffness = Spring.StiffnessLow)

private enum class BoxState {
    NORMAL,
    LARGE
}