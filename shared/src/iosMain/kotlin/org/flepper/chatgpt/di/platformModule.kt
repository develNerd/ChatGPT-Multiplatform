package org.flepper.chatgpt.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.flepper.chatgpt.data.datastore.AppDataStore
import org.flepper.chatgpt.data.datastore.createMainDataStore
import org.flepper.chatgpt.data.datastore.dataStoreFileName
import org.flepper.chatgpt.data.repositories.ChatRepository
import org.flepper.chatgpt.data.repositoryImpl.ChatRepositoryImp
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.*

actual fun platformModule() : Module = module {
    single { createDataStore() }
    single { AppDataStore(get()) }
    single<ChatRepository> { ChatRepositoryImp(get(),get(),get()) }
}

actual fun useCaseModule(): Module = module {

}

fun createDataStore(): DataStore<Preferences> = createMainDataStore(
    producePath = {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        requireNotNull(documentDirectory).path + "/$dataStoreFileName"
    }
)