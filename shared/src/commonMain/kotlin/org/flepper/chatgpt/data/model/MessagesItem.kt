package org.flepper.chatgpt.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.flepper.chatgpt.data.USER_ROLE
import kotlin.test.Ignore

@Serializable
data class MessagesItem(val role: String = USER_ROLE,
                        val id: String = "",
                        @Transient
                        val uuid:String = "",
                        val content: Content)