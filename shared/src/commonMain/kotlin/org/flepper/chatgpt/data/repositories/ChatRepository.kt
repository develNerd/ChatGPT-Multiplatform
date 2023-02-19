package org.flepper.chatgpt.data.repositories

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import org.flepper.chatgpt.data.model.ConversationsResponse
import org.flepper.chatgpt.data.model.MessageResponse
import org.flepper.chatgpt.data.model.MessagesItem
import org.flepper.chatgpt.data.model.ModerationRequest
import org.flepper.chatgpt.data.network.OnResultObtained

interface ChatRepository {
    val chatData:MutableSharedFlow<MessageResponse>
    val conversationsResponse:MutableSharedFlow<ConversationsResponse>
    suspend fun getChatData(question:MessagesItem): MutableStateFlow<OnResultObtained<Unit>>
    suspend fun streamPastConversation():StateFlow<OnResultObtained<Unit>>
}