package org.flepper.chatgpt.di

import org.flepper.chatgpt.data.network.Api
import org.flepper.chatgpt.data.network.ApiClient
import org.flepper.chatgpt.data.usecasefactories.ChatUseCaseFactory
import org.koin.dsl.module

val sharedModule = module {
    single{ ApiClient() }
    single { Api(get()) }
}

val useCaseModule = module {
    single { ChatUseCaseFactory(get()) }
}



