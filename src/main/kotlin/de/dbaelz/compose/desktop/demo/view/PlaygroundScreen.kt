package de.dbaelz.compose.desktop.demo.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

const val DEMO_TEXT = "Yet another text animation demo for Compose"

@Composable
fun PlaygroundScreen(onBackNavigation: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BackButton(onBackNavigation)

        TextInOut(DEMO_TEXT)

        Spacer(modifier = Modifier.height(32.dp))

        ReplaceCharactersInText(DEMO_TEXT)
    }
}

@Composable
fun CustomText(text: String) {
    Text(
        text = text,
        fontSize = 32.sp,
        fontFamily = FontFamily.Monospace,
        color = MaterialTheme.colors.primary,
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
    )
}

@Composable
fun TextInOut(text: String, animationTime: Int = 1500) {
    val transition = rememberInfiniteTransition()

    val dropText by transition.animateValue(
        0, text.length, Int.VectorConverter,
        InfiniteRepeatableSpec(
            animation = tween(animationTime),
            repeatMode = RepeatMode.Reverse
        )
    )
    CustomText(text.dropLast(dropText))
}

@Composable
fun ReplaceCharactersInText(
    text: String,
    replacementCharacter: Char = '*',
    animationTimePerChar: Int = 500
) {
    var animatedText by remember { mutableStateOf(text to 0) }
    var animationTimeLeft by remember { mutableStateOf(text.length * animationTimePerChar) }


    LaunchedEffect(animatedText) {
        if (animationTimeLeft > 0) {
            animationTimeLeft -= animationTimePerChar
            delay(animationTimePerChar.toLong())

            val charArray = animatedText.first.toCharArray()
            charArray[animatedText.second] = replacementCharacter

            animatedText = charArray.concatToString() to (animatedText.second + 1)
        }
    }
    CustomText(animatedText.first)
}