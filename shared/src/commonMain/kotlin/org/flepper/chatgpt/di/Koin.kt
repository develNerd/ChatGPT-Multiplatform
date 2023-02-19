package org.flepper.chatgpt.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(sharedModule, platformModule(), useCaseModule())
    }

// called by iOS etc
fun initKoin() = initKoin(enableNetworkLogs = false) {

}
