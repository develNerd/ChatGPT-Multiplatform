package org.flepper.chatgpt.android.presentation.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.flepper.chatgpt.android.presentation.core.BaseViewModel
import org.flepper.chatgpt.data.model.Content
import org.flepper.chatgpt.data.model.MessagesItem
import org.flepper.chatgpt.data.network.CONVERSATION_ID
import org.flepper.chatgpt.data.network.PARENT_MESSAGE_ID
import org.flepper.chatgpt.data.repositoryImpl.AuthRequest
import org.flepper.chatgpt.data.usecasefactories.AuthUseCaseFactory
import org.flepper.chatgpt.data.usecasefactories.ChatUseCaseFactory
import java.util.*


class HomeViewModel(
    private val chatUseCaseFactory: ChatUseCaseFactory,
    private val authUseCaseFactory: AuthUseCaseFactory
) : BaseViewModel() {

    private val currentMessageUUID = UUID.randomUUID().toString()
    var nextMapKey:Long = 3 // TODO("Replace with Size of Table rows when persisting')

    /** Euti Replies*/
    private val _chatGPTReplies = MutableStateFlow<Map<String,ChatType>>(
        mapOf(Pair("1", ChatType.ChatGPT("Hi there User", true)),
            Pair("2",ChatType.ChatGPT("How can I help you today ?", false)))
    )
    val chatGPTReplies: StateFlow<Map<String,ChatType>> = _chatGPTReplies

    /** @AddReply*/
    fun addToReplies(chatKey:String,chatType: ChatType) {
        val replies: MutableMap<String, ChatType> = chatGPTReplies.value.toMutableMap()
        replies[chatKey] = chatType
        _chatGPTReplies.value = replies
        if (chatType is ChatType.User){
            getChatConversation(chatType.text)
            setIsChatAdded(true)
        }
    }

    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading: StateFlow<Boolean> = _isChatLoading

    fun setIsChatLoading(value: Boolean) {
        _isChatLoading.value = value
    }

    private val _isChatAdded = MutableStateFlow<Boolean>(false)
    val isChatAdded: StateFlow<Boolean>
        get() = _isChatAdded

    fun setIsChatAdded(value: Boolean) {
        viewModelScope.launch {
            _isChatAdded.value = value
            delay(1000)
            _isChatAdded.value = false
        }
    }

    data class AuthResult(var authReady: Boolean? = null)



    private val _isAuthReady = MutableStateFlow(AuthResult())
    val isAuthReady: StateFlow<AuthResult>
        get() = _isAuthReady


    fun setIsAuthReady(value: Boolean){
        _isAuthReady.value = AuthResult(value)
    }

    fun checkIsAuthReady() {
        executeUseCase(Unit, authUseCaseFactory.checkAuthUseCase) { authSaved ->
            if (authSaved){
                streamPastConversations()
            }else{
                setIsAuthReady(false)
            }
        }
    }

    fun saveAuthRequest(bearer: String, cookie: String, userAgent: String) {
        executeUseCase(AuthRequest(bearer, userAgent, cookie), authUseCaseFactory.authUseCase)
    }




    private fun streamPastConversations() {
        executeApiUseCase(Unit, chatUseCaseFactory.getPreviousConversationsUseCase) { response ->
            if (response.error == null && response.isLoaded) {
                _isAuthReady.value = AuthResult(authReady = true)
            }else{
                _isAuthReady.value = AuthResult(authReady = false)
            }
        }
        executeStreamUseCase(
            Unit,
            chatUseCaseFactory.streamPastConversationsUseCase
        ) { response ->
            //Past Conversations
        }
    }




    private fun getChatConversation(question: String) {
        val currentMapKey = (0L..30000L).random()
        val messagesItem =
            MessagesItem(id = currentMessageUUID, uuid = UUID.randomUUID().toString() ,content = Content(parts = listOf(question)))
        var idSet = false
        setIsChatLoading(true)
        executeApiUseCase(messagesItem, chatUseCaseFactory.getChatUseCase){ result ->
            setIsChatLoading(false)
            viewModelScope.launch {
                delay(1000)
                setIsChatAdded(true)
            }
            if (result.isLoaded && result.error != null){
                Log.e("Result",result.toString())
            }else{
                //TODO("impl")
                _isAuthReady.value = AuthResult(authReady = false)
                Log.e("Result-Error",result.toString())
            }

        }
        executeStreamUseCase(
            Unit,
            chatUseCaseFactory.streamChatUseCase
        ) { messageResponseItem ->
            if (!idSet) {
                updateConversationIds(
                    messageResponseItem.conversationId,
                    messageResponseItem.message?.id ?: ""
                )
                idSet = true
            }
            messageResponseItem.conversationId
            val messagePart = messageResponseItem.message?.content?.parts?.firstOrNull()
            if (messagePart != null){
                addToReplies(messageResponseItem.message?.id ?: UUID.randomUUID().toString(), ChatType.ChatGPT(messagePart,true))
            }
            Log.e("Use CaseContent", messageResponseItem.toString())
        }
    }

    private fun updateConversationIds(conversationId: String, messageID: String) {
        val updateIds = mutableMapOf<String, String>()
        updateIds[CONVERSATION_ID] = conversationId
        updateIds[PARENT_MESSAGE_ID] = messageID
        executeUseCase(updateIds, authUseCaseFactory.updateMessageIDsUseCase)
    }



}
