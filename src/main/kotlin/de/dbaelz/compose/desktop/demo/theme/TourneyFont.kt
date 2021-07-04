package de.dbaelz.compose.desktop.demo.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp

private val Tourney = FontFamily(
    Font(
        resource = "fonts/tourney/Tourney-Bold.ttf",
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    ),
    Font(
        resource = "fonts/tourney/Tourney-Italic.ttf",
        weight = FontWeight.Normal,
        style = FontStyle.Italic
    ),
    Font(
        resource = "fonts/tourney/Tourney-Light.ttf",
        weight = FontWeight.Light,
        style = FontStyle.Normal
    ),
    Font(
        resource = "fonts/tourney/Tourney-Medium.ttf",
        weight = FontWeight.Medium,
        style = FontStyle.Normal
    ),
    Font(
        resource = "fonts/tourney/Tourney-Regular.ttf",
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    )
)

// Not a complete typography. Just for testing
val TourneyTypography = Typography(
    body1 = TextStyle(
        fontFamily = Tourney,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    )
)