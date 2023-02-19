package org.flepper.chatgpt.android.presentation.viewcomponets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.flepper.chatgpt.android.dimes.mediumPadding

@Composable
fun RoundedOutlinedButton(
    text: String,
    backgroundColor: Color = MaterialTheme.colors.background,
    contentColor: Color = MaterialTheme.colors.onBackground,
    modifier: Modifier = Modifier,
    borderStroke: Dp = 1.dp,
    borderColor: Color = contentColor,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(backgroundColor, contentColor),
        shape = RoundedCornerShape(100),
        modifier = modifier, border = BorderStroke(borderStroke,borderColor)
    ) {
        MediumTextBold(text = text, modifier = Modifier.padding(mediumPadding), color = contentColor)
    }
}