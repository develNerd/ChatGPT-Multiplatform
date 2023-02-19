package org.flepper.chatgpt.android.presentation.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.bumptech.glide.Glide
import org.flepper.chatgpt.android.R
import org.flepper.chatgpt.android.databinding.BotTypingLayoutBinding
import org.flepper.chatgpt.android.dimes.*
import org.flepper.chatgpt.android.presentation.viewcomponets.MediumTextBold
import org.flepper.chatgpt.android.theme.*

sealed class ChatType {
    class ChatGPT(
        val text: String,
        var isHead: Boolean,
        val customContent: @Composable () -> Unit = {}
    ) : ChatType()

    class User(val text: String, var isHead: Boolean) : ChatType()

    object Default
}


@Composable
fun ChatView(
    modifier: Modifier = Modifier,
    chats: List<ChatType>,
    paddingBottom: Dp = 0.dp,
    homeViewModel: HomeViewModel
) {
    val isChatAdded by homeViewModel.isChatAdded.collectAsState(false)
    val isChatLoading by homeViewModel.isChatLoading.collectAsState()
    val scrollState = rememberScrollState()
    val ottoChatReplies = chats.toMutableList()



    LaunchedEffect(key1 = isChatAdded) {
        scrollState.animateScrollTo(Int.MAX_VALUE)
    }



    Column(
        modifier = modifier.fillMaxSize()
            .padding(bottom = paddingBottom)
            .verticalScroll(scrollState)
            .fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(
            spacing6dp
        )
    ) {


        ottoChatReplies.forEachIndexed { index, chat ->
            Box {
                when (chat) {
                    is ChatType.ChatGPT -> {
                        EutiBotView(chat.text, chat.isHead)
                    }
                    is ChatType.User -> {
                        UserChatVIew(chat.text, chat.isHead)
                    }
                }
            }
        }
        if (isChatLoading) {
            EutiBotView("", true)
        }
    }

}

@Composable
fun EutiBotView(text: String, isHead: Boolean = false) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = startChatSpacing, end = endChatSpacing)
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalArrangement = Arrangement.spacedBy(smallPadding)
        ) {

            if (isHead) {
                Image(
                    painter = painterResource(id = R.drawable.chat_gpt_icon),
                    contentDescription = "",
                    modifier = Modifier.size(size24dp)
                )
            }
            Box(
                modifier = Modifier.background(
                    color = eutiChatBackgroundColor(isSystemInDarkTheme()),
                    shape = RoundedCornerShape(
                        topStart = if (isHead) 0.dp else size10dp,
                        topEnd = size10dp,
                        bottomEnd = size10dp,
                        bottomStart = size10dp
                    )
                )
            ) {
                if (text != "") {
                    MediumTextBold(
                        text = text,
                        modifier = Modifier.padding(
                            mediumPadding
                        ),
                        color =  MaterialTheme.colors.onSurface
                    )
                } else {
                    val context = LocalContext.current
                    Box(modifier = Modifier.padding(mediumPadding)) {
                        AndroidViewBinding(BotTypingLayoutBinding::inflate) {
                            imageTyping.apply {
                                Glide.with(context)
                                    .load(R.drawable.bot_typing)
                                    .into(this)
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun UserChatVIew(text: String, isHead: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = startChatSpacing, start = endChatSpacing)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .background(
                    color = contrastBlue,
                    shape = RoundedCornerShape(
                        topStart = size10dp,
                        topEnd = size10dp,
                        bottomEnd = if (isHead) 0.dp else size10dp,
                        bottomStart = size10dp
                    )
                )
        ) {
            MediumTextBold(
                text = text,
                modifier = Modifier.padding(
                    mediumPadding
                ),
                color = Color.White
            )
        }
    }

}