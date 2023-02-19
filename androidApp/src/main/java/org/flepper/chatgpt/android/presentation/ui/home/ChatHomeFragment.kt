package org.flepper.chatgpt.android.presentation.ui.home

import android.util.Log
import android.view.View
import android.webkit.*
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.flepper.chatgpt.android.BuildConfig
import org.flepper.chatgpt.android.presentation.core.BaseComposeFragment
import org.flepper.chatgpt.android.presentation.viewcomponets.MediumTextBold
import org.flepper.chatgpt.android.R
import org.flepper.chatgpt.android.databinding.FragmentUserAuthBinding
import org.flepper.chatgpt.android.dimes.*
import org.flepper.chatgpt.android.presentation.core.ColumnInset
import org.flepper.chatgpt.android.presentation.viewcomponets.OutLineEdittext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.UUID

class ChatHomeFragment : BaseComposeFragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var mainWebView: WebView? = null

    data class ScrollUpEvent(var shouldScroll:Boolean = false)

    @Composable
    override fun InitComposeUI() {
        val navController = rememberNavController()
        val isAuthReady by homeViewModel.isAuthReady.collectAsState()
        var currentBottomIndex by remember {
            mutableStateOf(1)
        }



        fun navigate(route:String){
            navController.navigate(route) {

                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                //launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        }
        currentBottomIndex = if (isAuthReady.authReady == true) {
            1
        } else {
            0
        }


        val items = listOf(
            Screen.WebClient,
            Screen.MobileClient
        )
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                ChatToolbar(
                    smallTitle = stringResource(id = R.string.chat_gpt),
                    icon = R.drawable.chat_gpt_icon,
                    items,
                    itemIndex = currentBottomIndex,
                ) {
                    currentBottomIndex = it
                    val route = when (items[it]) {
                        is Screen.WebClient -> {
                            //currentBottomIndex = 0
                            Screen.WebClient.route.routeName
                        }
                        is Screen.MobileClient -> {
                            //currentBottomIndex = 1
                            Screen.MobileClient.route.routeName
                        }
                    }
                    navController.navigate(route) {

                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        //launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            },
            bottomBar = {

            },
            content = {
                val chats by homeViewModel.chatGPTReplies.collectAsState()
                var chatText by remember {
                    mutableStateOf("")
                }


                val startDestination by remember {
                    derivedStateOf {
                        if (currentBottomIndex == 1) Screen.MobileClient.route.routeName else  Screen.WebClient.route.routeName
                    }
                }


                val focusManager = LocalFocusManager.current

                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    modifier = Modifier.padding(bottom = it.calculateBottomPadding())
                ) {
                    composable(Screen.WebClient.route.routeName) {

                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                        )
                        {
                            val paddingBottom = it.calculateBottomPadding()

                            AndroidViewBinding(
                                factory = FragmentUserAuthBinding::inflate, modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = paddingBottom)
                            ) {
                                mainWebView = webView
                                this.webView.apply {
                                    settings.javaScriptEnabled = true
                                    settings.userAgentString = BuildConfig.APPLICATION_ID
                                    settings.javaScriptCanOpenWindowsAutomatically = true
                                    settings.setSupportMultipleWindows(true)
                                    mainWebView!!.scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY

                                    loadUrl(CHAT_URL)

                                    webViewClient = CustomWebViewClient(
                                    ) { headers, cookie ->
                                        homeViewModel.saveAuthRequest(
                                            bearer = headers[AUTHORIZATION] ?: "",
                                            cookie = cookie,
                                            userAgent = BuildConfig.APPLICATION_ID
                                        )
                                        homeViewModel.setIsAuthReady(true)
                                    }
                                }
                            }
                        }

                    }
                    composable(Screen.MobileClient.route.routeName) {
                        ColumnInset(modifier = Modifier.fillMaxSize()) {
                            Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = spacing4dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    OutLineEdittext(
                                        hint = getString(R.string.question_here),
                                        text = chatText,
                                        stroke = BorderStroke(1.dp, Color.Gray),
                                        onFocused = {foc ->
                                                    if (foc){
                                                        homeViewModel.setIsChatAdded(true)
                                                    }
                                        },
                                        onTextChanged = { txt ->
                                            chatText = txt
                                        },
                                        modifier = Modifier
                                            .weight(0.85f)
                                    )
                                    IconButton(onClick = {
                                        if (chatText.trim().isNotEmpty()) {
                                            homeViewModel.addToReplies(
                                               UUID.randomUUID().toString(),
                                                ChatType.User(chatText, true)
                                            )
                                            homeViewModel.setIsChatAdded(true)
                                            focusManager.clearFocus()
                                        }
                                        chatText = ""
                                    }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_send_24),
                                            ""
                                        )
                                    }
                                }
                            }) { padding ->
                                ChatView(
                                    modifier = Modifier
                                        .padding(bottom = padding.calculateBottomPadding())
                                        .fillMaxSize(),
                                    chats = chats.values.toMutableList(),
                                    homeViewModel = homeViewModel,
                                )

                            }
                        }

                    }
                }
            })




    }

    override fun viewCreated() {
        homeViewModel.checkIsAuthReady()

    }

}

class CustomWebViewClient(
    private val headersFound: (MutableMap<String, String>, String) -> Unit
) :
    WebViewClient() {


    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {


        if (isAuthorised(request?.requestHeaders)) {
            val cookies: String = CookieManager.getInstance().getCookie(request?.url.toString())
            Log.e("TAG", "All the cookies in a string:$cookies")
            headersFound(request?.requestHeaders!!, cookies)
            Log.e("isAuthorised", "true")
        } else {
            Log.e("isAuthorised", "false")
        }
        return super.shouldInterceptRequest(view, request)
    }


    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

    }

    private fun isAuthorised(headers: MutableMap<String, String>?) =
        headers?.get(AUTHORIZATION) != null
}


sealed class Screen(val route: HomeRoutes, @DrawableRes val icon: Int) {
    object WebClient : Screen(route = HomeRoutes.WEB_VIEW, R.drawable.globe)
    object MobileClient : Screen(HomeRoutes.MOBILE_VIEW, R.drawable.mobile_chat)
}

@Composable
fun ChatToolbar(
    smallTitle: String = "",
    @DrawableRes icon: Int,
    navigations: List<Screen>,
    itemIndex: Int = 0,
    onNavItemClicked: (Int) -> Unit
) {

    Card() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = spacing12dp, horizontal = spacing8dp)
        ) {

            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                horizontalArrangement = Arrangement.spacedBy(spacing3dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (smallTitle.isNotEmpty()) {
                    MediumTextBold(
                        text = smallTitle,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }

            Card(
                elevation = size2dp,
                shape = RoundedCornerShape(100),
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Row(
                    modifier = Modifier
                        , verticalAlignment = Alignment.CenterVertically
                ) {
                    navigations.forEachIndexed { index, screen ->
                        val cornerShape = if (screen is Screen.WebClient) RoundedCornerShape(
                            topStartPercent = 100,
                            bottomStartPercent = 100
                        ) else RoundedCornerShape(bottomEndPercent = 100, topEndPercent = 100)
                        val alpha = if (index == itemIndex) 0.2F else 0.0F
                        Box(
                            Modifier
                                .background(Color.Gray.copy(alpha = alpha), cornerShape)
                                .padding(
                                    horizontal = spacing12dp, vertical = spacing4dp
                                )
                                .clickable(MutableInteractionSource(), null) {
                                    onNavItemClicked(index)

                                }) {
                            Image(
                                painter = painterResource(id = screen.icon),
                                contentDescription = "",
                                Modifier.size(
                                    size24dp
                                ),
                                colorFilter = if (itemIndex == index) ColorFilter.tint(MaterialTheme.colors.primary) else null,
                                contentScale = ContentScale.Fit
                            )
                        }
                    }

                }
            }


        }
    }


}



