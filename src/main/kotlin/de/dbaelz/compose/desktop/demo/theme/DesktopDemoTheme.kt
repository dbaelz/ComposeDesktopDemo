package de.dbaelz.compose.desktop.demo.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp


private val LightPalette = lightColors(
    primary = Color(0xff4fc3f7),
    primaryVariant = Color(0xff0093c4),
    onPrimary = Color(0xff000000),
    secondary = Color(0xfffdd835),
    secondaryVariant = Color(0xffc6a700),
    onSecondary = Color(0xff000000),
    onSurface = Color(0xff000000),
    onBackground = Color(0xff000000),
    error = Color(0xffff3d00),
    onError = Color(0xff000000)
)


@Composable
fun DesktopDemoTheme(
    isDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        // TODO: Add dark theme
        colors = if (isDarkTheme) LightPalette else LightPalette,
        // We could add the typography here for the theme. But the current is alright ;)
        // typography = TourneyTypography
    ) {
        Row(
            Modifier
                .border(
                    width = 16.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colors.secondary,
                            MaterialTheme.colors.primary
                        )
                    ),
                    shape = RectangleShape
                )
                .padding(16.dp)
                .fillMaxSize()
        ) {
            content()
        }
    }
}