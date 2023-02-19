package org.flepper.chatgpt.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.flepper.chatgpt.data.network.CHAT_MODEL

@Serializable
data class ConversationRequest(
    val parent_message_id:String = "",
    val action: String = "next",
    val messages: List<MessagesItem>? = emptyList(),
    val model: String = CHAT_MODEL,
   // val conversation_id: String = ""
)

