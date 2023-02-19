package org.flepper.chatgpt.android.presentation.core

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.flepper.chatgpt.android.dimes.spacing12dp
import org.flepper.chatgpt.android.dimes.spacing16dp
import org.flepper.chatgpt.android.dimes.spacing6dp
import org.flepper.chatgpt.android.dimes.spacing8dp

@Composable
fun ColumnInset(modifier: Modifier = Modifier, verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(
    spacing12dp
), content:@Composable () -> Unit){
    val windowHorizontalInset = spacing6dp

    Column(modifier.fillMaxSize().padding(start = windowHorizontalInset, end = windowHorizontalInset, top = spacing16dp, bottom = spacing8dp), verticalArrangement = verticalArrangement) {
        content()
    }
}
