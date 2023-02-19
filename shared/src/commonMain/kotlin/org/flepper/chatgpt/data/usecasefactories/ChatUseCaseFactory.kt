package org.flepper.chatgpt.data.usecasefactories

import org.flepper.chatgpt.data.repositories.ChatRepository
import org.flepper.chatgpt.data.usecases.GetChatConvoUseCase
import org.flepper.chatgpt.data.usecases.GetPreviousConversationsUseCase
import org.flepper.chatgpt.data.usecases.StreamChatUseCase
import org.flepper.chatgpt.data.usecases.StreamConversationsUseCase

class ChatUseCaseFactory(chatRepository: ChatRepository) {

    val getChatUseCase = GetChatConvoUseCase(chatRepository)
    val getPreviousConversationsUseCase = GetPreviousConversationsUseCase(chatRepository)
    val streamChatUseCase = StreamChatUseCase(chatRepository)
    val streamPastConversationsUseCase= StreamConversationsUseCase(chatRepository)
}