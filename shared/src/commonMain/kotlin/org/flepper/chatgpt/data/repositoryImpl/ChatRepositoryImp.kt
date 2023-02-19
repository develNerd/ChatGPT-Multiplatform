package org.flepper.chatgpt.data.repositoryImpl

import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.flepper.chatgpt.data.datastore.AppDataStore
import org.flepper.chatgpt.data.model.ConversationRequest
import org.flepper.chatgpt.data.model.ConversationsResponse
import org.flepper.chatgpt.data.model.MessageResponse
import org.flepper.chatgpt.data.model.MessagesItem
import org.flepper.chatgpt.data.network.Api
import org.flepper.chatgpt.data.network.OnResultObtained
import org.flepper.chatgpt.data.network.makeRequestToApi
import org.flepper.chatgpt.data.repositories.ChatRepository
import org.flepper.chatgpt.data.utils.ResponseParser
import org.flepper.chatgpt.data.utils.parseMessageResponse
import org.flepper.chatgpt.helpers.decodeBrotliBytes

class ChatRepositoryImp(private val api: Api, private val dataStore: AppDataStore,private val responseParser: ResponseParser) :
    ChatRepository {

    override val chatData = MutableSharedFlow<MessageResponse>()
    override val conversationsResponse = MutableSharedFlow<ConversationsResponse>()

    private var defConversationID = ""

    override suspend fun getChatData(question: MessagesItem): MutableStateFlow<OnResultObtained<Unit>> {
        val userAgent = Pair("user-agent", dataStore.userAgent.first())
        val authorization = Pair("Authorization", dataStore.authorization.first())
        val cookie = Pair("Cookie", dataStore.cookie.first())
        val conversationID = dataStore.conversationID.first()
        val parentMessageId = dataStore.currentMessageID.first()

        return makeRequestToApi {
            api.streamConversationParts(
                ConversationRequest(
                    messages = listOf(question),
                     parent_message_id = question.uuid
                ),
                headers = mutableMapOf(userAgent, authorization, cookie)
            ) { data ->
                val json = parseMessageResponse(data)
                if (json.trim().isNotEmpty()){
                    val messagePart =  responseParser.decodeTOObject<MessageResponse>(json)
                    if (messagePart != null) {
                        chatData.emit(messagePart)
                    }
                }
            }
        }.toOnResultObtained()
    }

    fun getMessagePart(){

    }

    override suspend fun streamPastConversation() = makeRequestToApi {
        api.streamPastConversations(getHeaders()) {
            val response = Json.decodeFromString<ConversationsResponse>(decodeBrotliBytes(it))
            defConversationID = response.items?.randomOrNull()?.id ?: ""
            dataStore.updateConversationID(response.items?.lastOrNull()?.id ?: "")
            conversationsResponse.emit(response)
        }
    }.toOnResultObtained<Unit>().asStateFlow()



    private suspend fun getHeaders() = run {
        val userAgent = Pair("user-agent", dataStore.userAgent.first())
        val authorization = Pair("Authorization", dataStore.authorization.first())
        val cookie = Pair("Cookie", dataStore.cookie.first())
        mutableMapOf(userAgent, authorization, cookie)
    }

}