package de.dbaelz.compose.desktop.demo.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

@Composable
fun TextAnimationScreen(onBackNavigation: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BackButton(onBackNavigation)

        TextInOut(DEMO_TEXT)

        Spacer(modifier = Modifier.height(32.dp))

        ReplaceCharactersInText(DEMO_TEXT)

        Spacer(modifier = Modifier.height(32.dp))

        ReplaceCharactersInText(DEMO_TEXT, infiniteRepeatMode = true)

        Spacer(modifier = Modifier.height(32.dp))

        ReplaceCharactersInText(DEMO_TEXT, replacementCharacter = '_', replaceWhiteSpace = false)

        Spacer(modifier = Modifier.height(32.dp))

        ReplaceCharactersInText(DEMO_TEXT, replacementCharacter = '_', replaceWhiteSpace = false, infiniteRepeatMode = true)

        Spacer(modifier = Modifier.height(32.dp))

        HighlightWords(DEMO_TEXT)

        Spacer(modifier = Modifier.height(32.dp))

        SwapCharactersInText(DEMO_TEXT)

        Spacer(modifier = Modifier.height(32.dp))

        SwapCharactersInText(DEMO_TEXT, infiniteRepeatMode = true)

        Spacer(modifier = Modifier.height(32.dp))

        ClickableText(onBackNavigation)
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
fun CustomText(
    animatedText: AnimatedText,
    textStyle: SpanStyle = defaultStyle(),
    highlightStyle: SpanStyle = highlightStyle()
) {
    val annotatedText = buildAnnotatedString {
        val currentIndex = animatedText.currentIndex
        val textLength = animatedText.text.length

        withStyle(textStyle) {
            append(animatedText.text.substring(0, currentIndex))
        }

        withStyle(highlightStyle) {
            append(
                animatedText.text.substring(
                    currentIndex,
                    min(currentIndex + 1, textLength)
                )
            )
        }

        withStyle(textStyle) {
            append(animatedText.text.substring(min(currentIndex + 1, textLength), textLength))
        }
    }

    Text(
        text = annotatedText,
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
    )
}

@Composable
fun CustomText(words: Words) {
    val annotatedText = buildAnnotatedString {
        val currentIndex = words.currentIndex

        words.words.forEachIndexed { index, word ->
            withStyle(specialHighlightStyle(index == currentIndex)) {
                append(word)
            }
            append(' ')
        }
    }

    Text(
        text = annotatedText,
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
    )
}

@Composable
fun TextInOut(text: String, animationTime: Int = 1500) {
    val transition = rememberInfiniteTransition()

    val textIndex by transition.animateValue(
        0, text.length, Int.VectorConverter,
        InfiniteRepeatableSpec(
            animation = tween(animationTime),
            repeatMode = RepeatMode.Reverse
        )
    )
    CustomText(text.dropLast(textIndex))
}

@Composable
fun ReplaceCharactersInText(
    text: String,
    replacementCharacter: Char = '*',
    replaceWhiteSpace: Boolean = true,
    infiniteRepeatMode: Boolean = false,
    animationTimePerChar: Int = 500
) {
    val animationTime = text.length * animationTimePerChar

    var animatedText by remember { mutableStateOf(AnimatedText(text, 0)) }
    var animationTimeLeft by remember { mutableStateOf(animationTime) }

    LaunchedEffect(animatedText) {
        if (animationTimeLeft > 0) {
            animationTimeLeft -= animationTimePerChar
            delay(animationTimePerChar.toLong())

            val charArray = animatedText.text.toCharArray()

            val currentChar = charArray[animatedText.currentIndex]

            if (replaceWhiteSpace || currentChar != ' ') {
                val newChar = if (animatedText.reversed) {
                    text[animatedText.currentIndex]
                } else {
                    replacementCharacter
                }

                charArray[animatedText.currentIndex] = newChar
            }

            val nextIndex = if (animatedText.reversed) {
                max(animatedText.currentIndex - 1, 0)
            } else {
                min(animatedText.currentIndex + 1, animatedText.text.length - 1)
            }

            println("index ${animatedText.reversed} ${animatedText.currentIndex} -> $nextIndex")
            animatedText = AnimatedText(charArray.concatToString(), nextIndex, animatedText.reversed)
        } else if (infiniteRepeatMode) {
            delay(animationTimePerChar.toLong() * 2)

            animationTimeLeft = animationTime
            animatedText = AnimatedText(
                animatedText.text,
                animatedText.currentIndex,
                reversed = !animatedText.reversed
            )
        }
    }
    CustomText(animatedText)
}

@Composable
fun SwapCharactersInText(
    text: String,
    infiniteRepeatMode: Boolean = false,
    animationTimePerChar: Int = 500
) {
    val animationTime = floor(text.length.toDouble() / 2).toInt() * animationTimePerChar

    var animatedText by remember { mutableStateOf(AnimatedText(text, 0)) }
    var animationTimeLeft by remember { mutableStateOf(animationTime) }

    LaunchedEffect(animatedText) {
        if (animationTimeLeft > 0) {
            animationTimeLeft -= animationTimePerChar
            delay(animationTimePerChar.toLong())

            val charArray = animatedText.text.toCharArray()

            val currentCharTemp = charArray[animatedText.currentIndex]
            val swapIndex = charArray.size - animatedText.currentIndex - 1

            charArray[animatedText.currentIndex] = charArray[swapIndex]
            charArray[swapIndex] = currentCharTemp


            animatedText = AnimatedText(charArray.concatToString(), animatedText.currentIndex + 1)
        } else if (infiniteRepeatMode) {
            delay(animationTimePerChar.toLong() * 2)

            animationTimeLeft = animationTime
            animatedText = AnimatedText(animatedText.text, 0)
        }
    }
    CustomText(animatedText.text)
}

@Composable
fun HighlightWords(
    text: String,
    animationTimePerWord: Int = 500
) {
    var highlightWords by remember { mutableStateOf(Words(text.split(" "), 0)) }
    var animationTimeLeft by remember { mutableStateOf(highlightWords.words.size * animationTimePerWord) }

    LaunchedEffect(highlightWords) {
        if (animationTimeLeft > 0) {

            animationTimeLeft -= animationTimePerWord
            delay(animationTimePerWord.toLong())


            highlightWords = highlightWords.withIncreasedIndex()
        } else {
            animationTimeLeft = highlightWords.words.size * animationTimePerWord
            highlightWords = highlightWords.withIncreasedIndex()
        }
    }
    CustomText(highlightWords)
}


@Composable
fun ClickableText(onBackNavigation: () -> Unit) {
    val annotatedText = buildAnnotatedString {
        withStyle(defaultStyle()) { append("Click ") }

        pushStringAnnotation(
            tag = CLICKABLE_LABEL, annotation = "Clicked!"
        )
        withStyle(highlightStyle()) { append("here") }

        pop()

        withStyle(defaultStyle()) { append(" to navigate back") }
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(
                tag = CLICKABLE_LABEL,
                start = offset,
                end = offset
            )
                .firstOrNull()?.let { annotation ->
                    onBackNavigation()
                }
        },
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
    )
}


@Composable
private fun defaultStyle() = SpanStyle(
    fontSize = 32.sp,
    color = MaterialTheme.colors.primary,
    fontFamily = FontFamily.Monospace
)

@Composable
private fun highlightStyle() = SpanStyle(
    fontSize = 32.sp,
    color = MaterialTheme.colors.secondary,
    fontFamily = FontFamily.Monospace
)

@Composable
private fun specialHighlightStyle(isFocused: Boolean = false): SpanStyle {
    return SpanStyle(
        fontSize = 32.sp,
        color = if (isFocused) MaterialTheme.colors.secondary else MaterialTheme.colors.primary,
        fontFamily = FontFamily.Monospace,
        fontWeight = if (isFocused) FontWeight.Bold else null,
        textDecoration = if (isFocused) TextDecoration.Underline else null,
    )
}


private const val DEMO_TEXT = "Yet another text animation"
private const val CLICKABLE_LABEL = "CLICKABLE"


data class AnimatedText(val text: String, val currentIndex: Int, val reversed: Boolean = false)

data class Words(val words: List<String>, var currentIndex: Int) {
    fun withIncreasedIndex(): Words {
        return Words(words, if (currentIndex + 1 > words.size) 0 else currentIndex + 1)
    }
}
