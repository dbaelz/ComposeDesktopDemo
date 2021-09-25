package de.dbaelz.compose.desktop.demo.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
    val animations = listOf<@Composable ColumnScope.() -> Unit>(
        { TextInOut(DEMO_TEXT) },
        { TextInOut(DEMO_TEXT, withEasing = false) },
        { ReplaceCharactersInText(DEMO_TEXT) },
        { ReplaceCharactersInText(DEMO_TEXT, infiniteRepeatMode = true) },
        { ReplaceCharactersInTextTransitionVersion(DEMO_TEXT) },
        {
            ReplaceCharactersInText(
                DEMO_TEXT,
                replacementCharacter = '_',
                replaceWhiteSpace = false
            )
        },
        {
            ReplaceCharactersInText(
                DEMO_TEXT,
                replacementCharacter = '_',
                replaceWhiteSpace = false,
                infiniteRepeatMode = true
            )
        },
        {
            ReplaceCharactersInTextTransitionVersion(
                DEMO_TEXT,
                replacementCharacter = '_',
                replaceWhiteSpace = false
            )
        },
        { HighlightWords(DEMO_TEXT) },
        { SwapCharactersInText(DEMO_TEXT) },
        { SwapCharactersInText(DEMO_TEXT, infiniteRepeatMode = true) },
        { ClickableText(onBackNavigation) }
    )

    Screen(
        { ScreenTopBar("Text Animation", onBackNavigation) },
        animations
    )
}

@Composable
fun CustomText(text: String) {
    Text(
        text = text,
        fontSize = defaultFontSize,
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
fun TextInOut(text: String, animationTime: Int = 1500, withEasing: Boolean = true) {
    val transition = rememberInfiniteTransition()

    val textIndex by transition.animateValue(
        0, text.length, Int.VectorConverter,
        InfiniteRepeatableSpec(
            animation = tween(
                durationMillis = animationTime,
                easing = if (withEasing) FastOutSlowInEasing else LinearEasing
            ),
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

            animatedText =
                AnimatedText(charArray.concatToString(), nextIndex, animatedText.reversed)
        } else if (infiniteRepeatMode) {
            delay(animationTimePerChar.toLong())

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
fun ReplaceCharactersInTextTransitionVersion(
    text: String,
    replacementCharacter: Char = '*',
    replaceWhiteSpace: Boolean = true,
    animationTimePerChar: Int = 500
) {
    val animationTime = text.length * animationTimePerChar

    var animatedText by remember { mutableStateOf(text to false) }

    val transition = rememberInfiniteTransition()
    val animationCounter by transition.animateValue(
        0, text.length + 2, Int.VectorConverter,
        InfiniteRepeatableSpec(
            animation = tween(
                durationMillis = animationTime,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(animationCounter) {
        val currentText = animatedText.first
        val currentReverse = animatedText.second
        val currentIndex = animationCounter - 1

        if (animationCounter == 0) {
            animatedText = currentText to false
        } else if (animationCounter <= currentText.length) {
            if (!replaceWhiteSpace && currentText[currentIndex] == ' ') {
                return@LaunchedEffect
            }

            val newText = if (currentReverse) {
                currentText.replaceRange(
                    currentIndex,
                    currentText.length,
                    text.substring(currentIndex, text.length)
                )
            } else {
                currentText.replaceRangeWitChar(
                    currentIndex,
                    currentIndex + 1,
                    replacementCharacter
                )
            }

            animatedText = newText to currentReverse
        } else {
            animatedText = currentText to true
        }
    }

    CustomText(animatedText.first)
}

private fun String.replaceRangeWitChar(
    startIndex: Int,
    endIndex: Int,
    replacementCharacter: Char
): String {
    return replaceRange(
        startIndex, endIndex,
        String(CharArray(endIndex - startIndex) { replacementCharacter })
    )
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
            ).firstOrNull()?.let { onBackNavigation() }
        },
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
    )
}

@Composable
private fun defaultStyle() = SpanStyle(
    fontSize = defaultFontSize,
    color = MaterialTheme.colors.primary,
    fontFamily = FontFamily.Monospace
)

@Composable
private fun highlightStyle() = SpanStyle(
    fontSize = defaultFontSize,
    color = MaterialTheme.colors.secondary,
    fontFamily = FontFamily.Monospace
)

@Composable
private fun specialHighlightStyle(isFocused: Boolean = false): SpanStyle {
    return SpanStyle(
        fontSize = defaultFontSize,
        color = if (isFocused) MaterialTheme.colors.secondary else MaterialTheme.colors.primary,
        fontFamily = FontFamily.Monospace,
        fontWeight = if (isFocused) FontWeight.Bold else null,
        textDecoration = if (isFocused) TextDecoration.Underline else null,
    )
}


private const val DEMO_TEXT = "Yet another text animation"
private const val CLICKABLE_LABEL = "CLICKABLE"

private val defaultFontSize = 32.sp

@Immutable
data class AnimatedText(val text: String, val currentIndex: Int, val reversed: Boolean = false)

data class Words(val words: List<String>, var currentIndex: Int) {
    fun withIncreasedIndex(): Words {
        return Words(words, if (currentIndex + 1 > words.size) 0 else currentIndex + 1)
    }
}
