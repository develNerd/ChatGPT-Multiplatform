package org.flepper.chatgpt.android.presentation.ui.home

import android.webkit.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.flepper.chatgpt.android.presentation.core.BaseComposeFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserAuthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserAuthFragment : BaseComposeFragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()

    private var mainWebView: WebView? = null

    @Composable
    override fun InitComposeUI() {

    }

    override fun viewCreated() {

    }


}




const val AUTHORIZATION = "Authorization"
const val COOKIE = "cookie"
const val USER_AGENT = "user-agent"
const val CHAT_URL = "https://chat.openai.com/chat"
