package org.flepper.chatgpt.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.flepper.chatgpt.data.datastore.AppDataStore
import org.flepper.chatgpt.data.datastore.createMainDataStore
import org.flepper.chatgpt.data.datastore.dataStoreFileName
import org.flepper.chatgpt.data.repositories.AuthRepository
import org.flepper.chatgpt.data.repositories.ChatRepository
import org.flepper.chatgpt.data.repositoryImpl.AuthRepositoryImpl
import org.flepper.chatgpt.data.repositoryImpl.ChatRepositoryImp
import org.flepper.chatgpt.data.usecasefactories.AuthUseCaseFactory
import org.flepper.chatgpt.data.usecasefactories.ChatUseCaseFactory
import org.flepper.chatgpt.data.utils.ResponseParser
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule() : Module = module {
    single { createDataStore(get()) }
    single { AppDataStore(get()) }
    single { ResponseParser() }
}


actual fun useCaseModule(): Module = module {
    single<AuthRepository> { AuthRepositoryImpl(get())  }
    single<ChatRepository> { ChatRepositoryImp(get(),get(),get())  }
    single { ChatUseCaseFactory(get()) }
    single { AuthUseCaseFactory(get()) }
}



fun createDataStore(context: Context): DataStore<Preferences> = createMainDataStore(
    producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
)
