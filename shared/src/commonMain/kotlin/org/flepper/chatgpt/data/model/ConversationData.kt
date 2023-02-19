package org.flepper.chatgpt.data.model

@kotlinx.serialization.Serializable
data class ConversationData(val conversationId: String = "",
                            val message: Message,
                            val error: String? = null)