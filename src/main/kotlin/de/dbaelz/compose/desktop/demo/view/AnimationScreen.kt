package de.dbaelz.compose.desktop.demo.view

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@ExperimentalAnimationApi
@Composable
fun AnimationScreen() {
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(backgroundColor, colors.background), 0.01f))
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            backgroundColorToggle = !backgroundColorToggle
        }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Toggle background")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                boxState = when (boxState) {
                    BoxState.NORMAL -> BoxState.LARGE
                    BoxState.LARGE -> BoxState.NORMAL
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Animate Box")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { boxVisible = !boxVisible },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Box Visibility")
        }

        Spacer(modifier = Modifier.height(8.dp))

        AnimatedBox(
            Modifier.align(Alignment.CenterHorizontally),
            boxVisible,
            boxColor,
            boxHeight,
            boxState != BoxState.NORMAL
        )
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
        enter = boxSlideInVertical(),
        exit = boxSlideOutVertical(),
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

@ExperimentalAnimationApi
fun boxSlideInVertical() = slideInVertically({ it / 2 }, boxTransition)

@ExperimentalAnimationApi
fun boxSlideOutVertical() = slideOutVertically({ it / 2 }, boxTransition)

val boxTransition: FiniteAnimationSpec<IntOffset> =
    spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow)

enum class BoxState {
    NORMAL,
    LARGE
}