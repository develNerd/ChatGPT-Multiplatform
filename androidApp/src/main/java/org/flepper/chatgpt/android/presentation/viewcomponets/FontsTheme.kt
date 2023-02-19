package org.flepper.chatgpt.android.presentation.viewcomponets

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.flepper.chatgpt.android.R

data class FontsTheme(
    val id:Int = R.font.poppins_regular,
    val fontFamily: FontFamily = poppins
)

val poppins = FontFamily(
    Font(R.font.poppins_regular),
    Font(R.font.poppins_bold, weight = FontWeight.Bold),
    Font(R.font.poppins_medium, weight = FontWeight.Medium),
    )

val default = FontsTheme(
    id = R.font.poppins_regular,
    fontFamily = poppins
)

val LocalFontThemes = compositionLocalOf { FontsTheme() }