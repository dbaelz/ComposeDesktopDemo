package de.dbaelz.compose.desktop.demo.view

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
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


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(backgroundColor, colors.background), 0.01f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                backButtonClicked = true
            },
            enabled = !backButtonClicked,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .offset(y = backButtonOffset)
        ) {
            Text("Back to menu")
        }

        Spacer(modifier = Modifier.height(32.dp))

        DotsLoadingIndicator(
            numberDots = 4,
            colorDot = colors.primary,
            colorHighlight = colors.secondaryVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        AnimationButton(
            "Toggle background",
            !backButtonClicked,
            Modifier.align(Alignment.CenterHorizontally)
        ) {
            backgroundColorToggle = !backgroundColorToggle
        }

        Spacer(modifier = Modifier.height(8.dp))

        AnimationButton(
            "Animate Box",
            !backButtonClicked,
            Modifier.align(Alignment.CenterHorizontally)
        ) {
            boxState = when (boxState) {
                BoxState.NORMAL -> BoxState.LARGE
                BoxState.LARGE -> BoxState.NORMAL
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        AnimationButton(
            "Box Visibility",
            !backButtonClicked,
            Modifier.align(Alignment.CenterHorizontally)
        ) {
            boxVisible = !boxVisible
        }

        Spacer(modifier = Modifier.height(8.dp))

        AnimatedBox(
            Modifier.align(Alignment.CenterHorizontally),
            boxVisible && !backButtonClicked,
            boxColor,
            boxHeight,
            boxState != BoxState.NORMAL
        )
    }
}

@Composable
fun AnimationButton(text: String, enabled: Boolean, modifier: Modifier, onClick: () -> Unit) {
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
fun AnimatedBox(
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
                .height(boxHeight)
                .width(132.dp)
                .animateContentSize()
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
fun DotsLoadingIndicator(
    numberDots: Int = 3,
    millisPerDot: Int = 300,
    colorDot: Color = MaterialTheme.colors.primary,
    colorHighlight: Color = MaterialTheme.colors.secondary,
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
                    .size(if (dotsAnimation == it) 24.dp else 16.dp)
                    .clip(CircleShape)
                    .background(if (dotsAnimation == it) colorHighlight else colorDot)
                    .align(Alignment.CenterVertically)
                    .animateContentSize()
            )
        }
    }
}

private val boxAnimationSpec: FiniteAnimationSpec<IntSize> = spring(stiffness = Spring.StiffnessLow)

private enum class BoxState {
    NORMAL,
    LARGE
}