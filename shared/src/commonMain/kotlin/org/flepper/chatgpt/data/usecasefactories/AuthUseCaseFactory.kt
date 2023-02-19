package org.flepper.chatgpt.data.usecasefactories

import org.flepper.chatgpt.data.repositories.AuthRepository
import org.flepper.chatgpt.data.usecases.AuthUseCase
import org.flepper.chatgpt.data.usecases.CheckAuthUseCase
import org.flepper.chatgpt.data.usecases.UpdateMessageIDsUseCase

class AuthUseCaseFactory(authRepository: AuthRepository)  {
    val authUseCase = AuthUseCase(authRepository)
    val checkAuthUseCase = CheckAuthUseCase(authRepository)
    val updateMessageIDsUseCase = UpdateMessageIDsUseCase(authRepository)

}