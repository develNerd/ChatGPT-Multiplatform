package org.flepper.chatgpt.data.usecases

import kotlinx.coroutines.flow.asSharedFlow
import org.flepper.chatgpt.data.model.ConversationsResponse
import org.flepper.chatgpt.data.model.MessageResponse
import org.flepper.chatgpt.data.model.MessagesItem
import org.flepper.chatgpt.data.repositories.ChatRepository


class  GetChatConvoUseCase(private val repository: ChatRepository): StateFlowBaseUseCase<MessagesItem, Unit>() {
    override suspend fun dispatch(input: MessagesItem) =  repository.getChatData(input)
}

class  StreamChatUseCase(private val repository: ChatRepository): ChatBaseUseCase<Unit, MessageResponse>() {
    override suspend fun dispatch(input: Unit) =  repository.chatData.asSharedFlow()
}


class  StreamConversationsUseCase(private val repository: ChatRepository): ChatBaseUseCase<Unit, ConversationsResponse>() {
    override suspend fun dispatch(input: Unit) =  repository.conversationsResponse.asSharedFlow()
}