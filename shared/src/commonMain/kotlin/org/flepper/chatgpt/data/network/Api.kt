package org.flepper.chatgpt.data.network

import org.flepper.chatgpt.data.model.ConversationRequest
import org.flepper.chatgpt.data.model.ConversationsResponse
import org.flepper.chatgpt.data.model.ModerationRequest


class Api(private val apiClient: ApiClient) {

    suspend fun streamConversationParts(conversationRequest: ConversationRequest,headers:MutableMap<String,String>,onStreamed:suspend (String) -> Unit) {
        apiClient.streamPost(NETWORK_ROUTES.CONVERSATION_BACKEND,null,conversationRequest, header = headers){stream ->
            onStreamed(stream)
        }
    }


    suspend fun streamPastConversations(headers:MutableMap<String,String>,onStreamed:suspend (ByteArray) -> Unit) {
        apiClient.streamGet<Unit>(NETWORK_ROUTES.GET_CONVERSATIONS, queryPair = listOf(Pair("offset","0"), Pair("limit","20")),body = null, header = headers){stream ->
            onStreamed(stream)
        }
    }

    suspend fun getPreviousConversations(headers: MutableMap<String, String>):ByteArray{
        return apiClient.GET(NETWORK_ROUTES.GET_CONVERSATIONS,headers,
            queryPair = listOf(Pair("offset","0"), Pair("limit","20")))
    }


    suspend fun streamModerationParts(conversationRequest: ModerationRequest,headers:MutableMap<String,String>,onStreamed:suspend (String) -> Unit) {
        apiClient.streamPost(NETWORK_ROUTES.MODERATION_BACKEND,null,conversationRequest, header = headers){stream ->
            onStreamed(stream)
        }
    }

}