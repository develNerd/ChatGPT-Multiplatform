package org.flepper.chatgpt.data.model

@kotlinx.serialization.Serializable
data class ModerationRequest(val input: String = "",
                             val conversationId: String = "",
                             val model: String = "text-moderation-playground",
                             val messageId: String = "")