package org.flepper.chatgpt.data.utils
fun  parseMessageResponse(data: String): String {
    return data.replaceFirst(":","^").split("^").getOrNull(1)?.trim() ?: data
}