package org.flepper.chatgpt

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform