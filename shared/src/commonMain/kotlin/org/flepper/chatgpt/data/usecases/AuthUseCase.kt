package org.flepper.chatgpt.data.usecases

import org.flepper.chatgpt.data.repositories.AuthRepository
import org.flepper.chatgpt.data.repositoryImpl.AuthRequest


class AuthUseCase(private val repository: AuthRepository): BaseUseCase<AuthRequest, Unit>() {
    override suspend fun dispatch(input: AuthRequest) =  repository.saveUserAuthorization(input)
}

class CheckAuthUseCase(private val repository: AuthRepository): BaseUseCase<Unit, Boolean>() {
    override suspend fun dispatch(input: Unit) =  repository.checkIsAuthReady()
}


class UpdateMessageIDsUseCase(private val repository: AuthRepository): BaseUseCase<Map<String,String>, Unit>() {
    override suspend fun dispatch(input: Map<String,String>) =  repository.updateCurrentConversationIds(input)
}
