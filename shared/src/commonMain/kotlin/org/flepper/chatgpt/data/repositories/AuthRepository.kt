package org.flepper.chatgpt.data.repositories

import kotlinx.coroutines.flow.Flow
import org.flepper.chatgpt.data.repositoryImpl.AuthRequest

interface AuthRepository {
    fun saveUserAuthorization(authRequest: AuthRequest)
    suspend fun checkIsAuthReady(): Boolean
    //TODO("Should match a specific Id in Db)
    fun updateCurrentConversationIds(ids:Map<String,String>)
}