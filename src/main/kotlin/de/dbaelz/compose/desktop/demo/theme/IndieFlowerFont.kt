package de.dbaelz.compose.desktop.demo.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

val IndieFlower = FontFamily(
    Font(
        resource = "fonts/indieflower/IndieFlower-Regular.ttf",
        weight = FontWeight.Normal,
        style = FontStyle.Normal
    ),

)

val IndieFlowerTypography = Typography(defaultFontFamily = IndieFlower)