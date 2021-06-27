package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun ClickableTextScreen(onBackNavigation: () -> Unit) {
    val annotatedText = buildAnnotatedString {
        withStyle(defaultStyle()) { append("Another text. Click ") }

        pushStringAnnotation(
            tag = CLICKABLE_LABEL, annotation = "Clicked!"
        )
        withStyle(clickStyle()) { append("here") }

        withStyle(defaultStyle()) { append(" to navigate back.") }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.primary,
                        MaterialTheme.colors.primaryVariant,
                        MaterialTheme.colors.secondaryVariant,
                        MaterialTheme.colors.secondary
                    )
                ),
                alpha = 0.2f
            )
    ) {
        ClickableText(
            text = annotatedText,
            onClick = { offset ->
                annotatedText.getStringAnnotations(
                    tag = CLICKABLE_LABEL,
                    start = offset,
                    end = offset
                )
                    .firstOrNull()?.let { annotation ->
                        println(annotation)
                        onBackNavigation()
                    }
            },
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun defaultStyle() = SpanStyle(
    fontSize = 32.sp,
    color = MaterialTheme.colors.primary
)

@Composable
private fun clickStyle() = SpanStyle(
    fontSize = 32.sp,
    color = MaterialTheme.colors.onBackground
)

private const val CLICKABLE_LABEL = "CLICKABLE"