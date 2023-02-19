package org.flepper.chatgpt.android.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColorPalette = lightColors(
    primary = colorPrimary,
    primaryVariant = colorPrimaryDark,
    secondary = colorAccent,
    surface = Color.White,
    onSurface = onBackGroundLight,
    secondaryVariant = textGray,
    onSecondary = systemGray,
    onBackground = textColorDark
    /* Other default colors to override
      background = Color.White,
      surface = Color.White,
      onPrimary = Color.White,
      onSecondary = Color.Black,
      onBackground = Color.Black,
      onSurface = Color.Black,
      */
)


private val DarkColorPalette = darkColors(
    primary = colorPrimary,
    primaryVariant = colorPrimaryDark,
    secondary = colorAccent,
    surface = surfaceForDark,
    onSurface = colorWhite,
    secondaryVariant = textGray,
    onSecondary = transBlackDark,
    background = darkBackground,
    onBackground = textWhite
    /* Other default colors to override
      background = Color.White,
      surface = Color.White,
      onPrimary = Color.White,
      onSecondary = Color.Black,
      onBackground = Color.Black,
      onSurface = Color.Black,
      */
)


@Composable
fun ChatGPTTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )

}