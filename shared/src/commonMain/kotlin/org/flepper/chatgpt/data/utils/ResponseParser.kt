package org.flepper.chatgpt.data.utils

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.flepper.chatgpt.data.model.MessageResponse

class ResponseParser() {
    inline  fun <reified T> decodeTOObject(json:String) : T? {
        return try {
            Json{this.ignoreUnknownKeys = true}.decodeFromString<T>(json)
        }catch (e:Exception){
            null
        }
    }
}