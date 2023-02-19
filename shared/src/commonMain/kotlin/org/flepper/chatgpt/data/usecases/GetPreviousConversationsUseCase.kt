package org.flepper.chatgpt.data.usecases

import org.flepper.chatgpt.data.model.ConversationsResponse
import org.flepper.chatgpt.data.repositories.ChatRepository

class  GetPreviousConversationsUseCase(private val repository: ChatRepository): StateFlowBaseUseCase<Unit, Unit>() {
    override suspend fun dispatch(input: Unit) =  repository.streamPastConversation()
}
