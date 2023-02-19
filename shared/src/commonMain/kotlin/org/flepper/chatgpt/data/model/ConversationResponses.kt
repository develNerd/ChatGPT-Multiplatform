package org.flepper.chatgpt.data.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ItemsItem(
    @SerialName("create_time")
    val createTime: String = "",
    val id: String = "",
    val title: String = ""
)


@kotlinx.serialization.Serializable
data class ConversationsResponse(
    val total: Int = 0,
    val offset: Int = 0,
    val limit: Int = 0,
    val items: List<ItemsItem>?
)


