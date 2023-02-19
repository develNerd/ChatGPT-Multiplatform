package org.flepper.chatgpt.android.theme

import androidx.compose.ui.graphics.Color


val colorPrimary = Color(0xFF90C71C)
val colorPrimaryDark = Color(0xFF6B9512)
val colorAccent = Color(0xFF008095)
val colorWhite = Color(0xFFFFFFFF)
val systemGray = Color(0xFFF0ECEC)
val lightGreenTrans64 = Color(0xFFECFBD9)
val grayDark = Color(0xFFB6B2B2)
val systemGrayText = Color(0xFF9FA6AD)
val systemGrayDark = Color(0xFF626E77)
val transWhite = Color(0x4FFFFFFF)

//Gray Colors

val anchorGreen = Color(0xFF123F39)
val contrastBlue = Color(0xFF008095)
val darkBackground = Color(0xFF303030)
fun bottomNavBackground(isDark:Boolean) = if (isDark)  Color(0xFF252525) else Color.White

val grayBackground = Color(0xFFF4F4F4)
val functionalRed = Color(0xFFEB000E)
val grayBlack = Color(0xB9000000)
val surfaceForDark = Color(0xFF1D1818)

val transBlackLighter = Color(0x1B000000)
val transBlackLight = Color(0x34000000)
val transBlack = Color(0x40000000)
val transBlackDark = Color(0x5B000000)
val white = Color(0x72FFFFFF)
val semiTransBlack = Color(0x7A000000)

//Blue Colors

val gray = Color(0xFF79838B)
val textGray = Color(0xFF626E77)
val textWhite = Color(0xFFD1D7DB)
val transGray = Color(0x41626E77)
val textColorDark = Color(0xC8000000)
val onBackGroundLight = Color(0xFF413F3F)
val borderColor = Color(0xFFE1E5E8)
val grayTrans50 = Color(0x80948686)
val grayTrans20 = Color(0x33F4E6E6)
fun eutiChatBackgroundColor(isDark:Boolean) = if (isDark) grayTrans50 else systemGray
fun mainListBottomItemColor(isDark:Boolean) = if (isDark) transGray else systemGray

/* Random Event Colors*/
val lightRed = Color(0xFFC71C3B)
val lightViolet = Color(0xFF901CC7)
val paleGreen = Color(0xFF1CC775)
val tintGreen = Color(0xFF1CC7BD)
val erandiPink = Color(0xFFC71C97)
val getBackground = Color(0xFF333132)

val _backGroundColors = mutableListOf(lightViolet, paleGreen, tintGreen, erandiPink, lightRed)
val eventColors = listOf(lightRed, lightViolet, paleGreen, tintGreen, erandiPink)
