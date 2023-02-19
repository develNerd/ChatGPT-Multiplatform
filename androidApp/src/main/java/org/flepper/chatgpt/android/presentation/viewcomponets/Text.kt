package org.flepper.chatgpt.android.presentation.viewcomponets

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun LargeBoldText(text:String?, modifier: Modifier = Modifier, color: Color = MaterialTheme.colors.onBackground, fontSize: TextUnit = 20.sp, fontStyle: FontStyle? = null, lineHeight: TextUnit = 50.sp){
    Text(modifier = modifier,text = text ?: "", fontFamily = LocalFontThemes.current.fontFamily , color = color,fontSize = fontSize,fontStyle = fontStyle, fontWeight =  FontWeight.Bold)
}

@Composable
fun MediumTextBold(text:String?, modifier: Modifier = Modifier, textSize: TextUnit = 16.sp, color: Color = MaterialTheme.colors.onSurface, textAlign: TextAlign = TextAlign.Start, lineHeight: TextUnit = TextUnit.Unspecified, fontWeight: FontWeight = FontWeight.Medium, fontStyle: FontStyle? = null, maxLines:Int = Int.MAX_VALUE){
    Text(text ?: "", modifier = modifier , textAlign = textAlign,fontFamily = LocalFontThemes.current.fontFamily , color = color,fontSize = textSize, fontWeight = fontWeight, lineHeight = lineHeight, fontStyle = fontStyle, maxLines = maxLines, overflow = TextOverflow.Ellipsis)
}

@Composable
fun TextHeader(text:String?, modifier: Modifier = Modifier, textSize: TextUnit = 16.sp, color: Color = MaterialTheme.colors.onSurface, textAlign: TextAlign = TextAlign.Start, lineHeight: TextUnit = TextUnit.Unspecified, fontWeight: FontWeight = FontWeight.Medium, fontStyle: FontStyle? = null){
    Text(text ?: "", modifier = modifier , textAlign = textAlign,fontFamily = LocalFontThemes.current.fontFamily , color = color,fontSize = textSize, fontWeight = fontWeight, lineHeight = lineHeight, fontStyle = fontStyle, maxLines = 1, overflow = TextOverflow.Ellipsis)
}

@Composable
fun RegularText(text:String?, modifier: Modifier = Modifier, color: Color = MaterialTheme.colors.onSurface, size: TextUnit = 14.sp, textAlign: TextAlign = TextAlign.Start){
    Text(text ?: "", modifier = modifier ,fontFamily = LocalFontThemes.current.fontFamily , color = color,fontSize = size, fontWeight =  FontWeight.Normal, textAlign = textAlign)
}

@Composable
fun RegularTextContent(text:String?, modifier: Modifier = Modifier, color: Color = MaterialTheme.colors.onSurface, size: TextUnit = 18.sp, textAlign: TextAlign = TextAlign.Start){
    Text(text ?: "", modifier = modifier.fillMaxHeight() ,fontFamily = LocalFontThemes.current.fontFamily , color = color,fontSize = size, fontWeight =  FontWeight.Normal, textAlign = textAlign, maxLines = Int.MAX_VALUE, overflow = TextOverflow.Visible)
}