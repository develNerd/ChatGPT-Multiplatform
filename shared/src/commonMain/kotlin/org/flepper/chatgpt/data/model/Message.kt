package org.flepper.chatgpt.data.model

@kotlinx.serialization.Serializable
data class Message(val updateTime: String? = null,
                   val role: String = "",
                   val createTime: String? = null,
                   val recipient: String = "",
                   val weight: Int = 0,
                   val id: String = "",
                   val endTurn: String? = null,
                   val user: String? = null,
                   val content: Content)