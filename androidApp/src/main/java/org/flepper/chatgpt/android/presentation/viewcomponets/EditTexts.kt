package org.flepper.chatgpt.android.presentation.viewcomponets

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import org.flepper.chatgpt.android.dimes.outlineEdittextRoundness
import org.flepper.chatgpt.android.theme.systemGrayText


@Composable
fun OutLineEdittext(modifier: Modifier = Modifier, hint:String, text:String, backgroundColor : Color = Color.Transparent,stroke: BorderStroke,onFocused:(Boolean) -> Unit, onTextChanged:(String) -> Unit){

    Box(modifier = modifier.fillMaxWidth().background(backgroundColor, shape = RoundedCornerShape(
        outlineEdittextRoundness
    )).border(
        stroke, shape = RoundedCornerShape(outlineEdittextRoundness))) {

        TextField(
            value = text,
            onValueChange = onTextChanged,
            placeholder = { Text(hint, color = systemGrayText) },
            modifier = Modifier.onFocusEvent {focusState ->
                if (focusState.isFocused) {
                    Log.e("Focused","true")
                    onFocused(true)
                }else{
                    onFocused(false)
                }
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent, textColor = MaterialTheme.colors.onSurface)
        )
    }

}


