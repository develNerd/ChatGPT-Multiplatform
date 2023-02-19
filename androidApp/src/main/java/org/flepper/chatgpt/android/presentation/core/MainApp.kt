package org.flepper.chatgpt.android.presentation.core

import android.app.Application
import org.flepper.chatgpt.di.initKoin
import org.flepper.chatgpt.android.presentation.ui.home.HomeViewModel
import org.flepper.chatgpt.di.platformModule
import org.flepper.chatgpt.di.sharedModule
import org.flepper.chatgpt.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.dsl.module

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

        //Initialise Koin for Dependency Injection
        initKoin {
            allowOverride(true)
            androidContext(this@MainApp)
            // if (BuildConfig.DEBUG)
            androidLogger(Level.ERROR)
            modules(platformModule() ,sharedModule, appModules,  useCaseModule())
        }

    }


}

val presentationModule = module {
    single { HomeViewModel(get(),get()) }
}
val appModules =  presentationModule
