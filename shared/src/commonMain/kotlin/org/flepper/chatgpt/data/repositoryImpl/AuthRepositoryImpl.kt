package org.flepper.chatgpt.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.flepper.chatgpt.data.datastore.AppDataStore
import org.flepper.chatgpt.data.network.CONVERSATION_ID
import org.flepper.chatgpt.data.network.PARENT_MESSAGE_ID
import org.flepper.chatgpt.data.repositories.AuthRepository

class AuthRepositoryImpl(private val dataStore: AppDataStore) : AuthRepository {
    override fun saveUserAuthorization(authRequest: AuthRequest) {
        dataStore.apply {
            updateAuthorization(authRequest.bearerToken)
            updateUserAgent(authRequest.userAgent)
            updateCookie(authRequest.cookie)
        }
    }

    override suspend fun checkIsAuthReady(): Boolean {
        return dataStore.authorization.first().isNotEmpty()
    }

    override fun updateCurrentConversationIds(ids:Map<String,String>) {
        //TODO("Should match a specific Id in Db)

        dataStore.apply {
            updateConversationID(ids[CONVERSATION_ID] ?: "")
            updateCurrentMessageId(ids[PARENT_MESSAGE_ID] ?: "")
        }
    }

}

data class AuthRequest(
    val bearerToken: String,
    val userAgent: String,
    val cookie: String
)