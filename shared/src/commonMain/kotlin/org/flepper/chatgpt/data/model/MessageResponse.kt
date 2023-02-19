package org.flepper.chatgpt.data.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class MainMessage(
    @SerialName("update_time")
    val updateTime: String? = null,
    val metadata: Metadata,
    val role: String? = "",
    @SerialName("create_time")
    val createTime: String? = null,
    val recipient: String = "",
    val id: String? = "",
    val user: String? = null,
    val content: MessageContent? =null
)


@kotlinx.serialization.Serializable
data class MessageContent(
    val contentType: String? = "",
    val parts: List<String>? = null
)


@kotlinx.serialization.Serializable
data class MessageResponse(
    @SerialName("conversation_id")
    val conversationId: String = "",
    val message: MainMessage? = null,
    val error: String? = null
)


@kotlinx.serialization.Serializable
data class Metadata(
    @SerialName("model_slug")
    val modelSlug: String? = "",
    @SerialName("message_type")
    val messageType: String? = ""
)


