package org.flepper.chatgpt.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.flepper.chatgpt.data.network.CHAT_TYPE

@Serializable
data class Content(@SerialName("content_type") val contentType: String = CHAT_TYPE,
                   val parts: List<String>?)