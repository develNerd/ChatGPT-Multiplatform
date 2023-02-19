package org.flepper.chatgpt.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okio.Path.Companion.toPath

class AppDataStore(private val dataStore: DataStore<Preferences>){

    private val scope = CoroutineScope(Dispatchers.Default)

    var authorization = getString(AUTHORIZATION)
    fun updateAuthorization(value: String)  =  saveToDatastore(AUTHORIZATION,value)

    var cookie = getString(COOKIE)
    fun updateCookie(value: String) = saveToDatastore(COOKIE,value)

    var userAgent = getString(USER_AGENT)
    fun updateUserAgent(value: String) = saveToDatastore(USER_AGENT,value)

    var conversationID = getString(CONVERSATION_ID)
    fun updateConversationID(value: String)  =  saveToDatastore(CONVERSATION_ID,value)


    var currentMessageID = getString(PARENT_MESSAGE_ID)
    fun updateCurrentMessageId(value: String)  =  saveToDatastore(PARENT_MESSAGE_ID,value)



    private  fun getLong(key:Preferences.Key<Long>): Flow<Long> = dataStore.data.map { pref ->
        pref[key] ?: 0L
    }


    private fun getString(key:Preferences.Key<String>): Flow<String> = dataStore.data.map { pref ->
        pref[key] ?: ""
    }

    private fun  <T> saveToDatastore(key: Preferences.Key<T>,value: T) =  scope.launch {
        dataStore.edit { settings ->
            settings[key] = value
        }
    }


    companion object {
        val AUTHORIZATION = stringPreferencesKey("AUTHORIZATION_BEARER")
        val COOKIE = stringPreferencesKey("COOKIE_")
        val USER_AGENT = stringPreferencesKey("USER_AGENT")
        //Refactor
        val CONVERSATION_ID = stringPreferencesKey("message.CONVERSATION_ID")
        val PARENT_MESSAGE_ID =  stringPreferencesKey("message.PARENT_MESSAGE_ID")
    }
}



fun createMainDataStore(producePath: () -> String): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        corruptionHandler = null,
        migrations = emptyList(),
        scope = CoroutineScope(Dispatchers.Default + SupervisorJob()),
        produceFile = { producePath().toPath() },
    )
}

internal const val dataStoreFileName = "fl.preferences_pb"