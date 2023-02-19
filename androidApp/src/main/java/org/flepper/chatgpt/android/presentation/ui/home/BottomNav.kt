package org.flepper.chatgpt.android.presentation.ui.home

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import org.flepper.chatgpt.android.R
import org.flepper.chatgpt.android.dimes.*
import org.flepper.chatgpt.android.presentation.viewcomponets.RegularText
import org.flepper.chatgpt.android.theme.*

@Composable
fun HomeBottomNav(items: List<Screen>, initialIndex: Int = 0, onClick:(Screen) -> Unit) {

    Surface(elevation = elevation16) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(bottomNavBackground(isSystemInDarkTheme())),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEachIndexed { index, bottomScreen ->
                BottomNavItem(
                    item = bottomScreen,
                    index = index,
                    isSelected = index == initialIndex,
                    this
                ) { selectedIndex ->
                    onClick(items[selectedIndex])
                }
            }

        }
    }

}

@Composable
fun BottomNavItem(
    item: Screen,
    index: Int,
    isSelected: Boolean,
    rowScope: RowScope,
    setIndex: (Int) -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    rowScope.apply {
        Column(
            verticalArrangement = Arrangement.spacedBy(spacing4dp),
            modifier = Modifier
                .weight(1f)
                .clickable(
                    indication = rememberRipple(bounded = true),
                    interactionSource = interactionSource
                ) { setIndex(index) },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.padding(bottom = spacing3dp)) {
                Box(
                    modifier = Modifier
                        .height(size1dot5dp)
                        .background(color = if (!isSelected) Color.Transparent else MaterialTheme.colors.primary)
                        .width(width24dp)
                )
            }

            Image(
                painter = painterResource(id = item.icon),
                contentDescription = "",
                androidx.compose.ui.Modifier.size(
                    size24dp
                ),
                colorFilter = if ( isSelected) ColorFilter.tint(MaterialTheme.colors.primary) else null,
                contentScale = ContentScale.Fit
            )
            RegularText(
                text = getRouteName(LocalContext.current,item.route) ,
                color = if (!isSelected) MaterialTheme.colors.onSurface else MaterialTheme.colors.primary
            )

        }
    }
}

fun getRouteName(context: Context,routes: HomeRoutes):String{
    return if(routes == HomeRoutes.WEB_VIEW) context.getString(R.string.web_client) else  context.getString(R.string.mobile_client)
}