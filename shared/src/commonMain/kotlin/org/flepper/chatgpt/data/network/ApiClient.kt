package org.flepper.chatgpt.data.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.cbor.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import io.ktor.utils.io.charsets.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.flepper.chatgpt.data.appBaseUrl

class ApiClient() {
    private val THIRTY_SECONDS = 900_000_000L// Equivalent to 60 Sec
    private val scope = CoroutineScope(Dispatchers.Default)

    val httpClient = HttpClient {



        install(ContentNegotiation) {
            json()
        }

        install(ContentEncoding) {
            deflate(1.0F)
            gzip(0.9F)

        }

        request {
            timeout {
                requestTimeoutMillis = THIRTY_SECONDS
            }
        }


        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    co.touchlab.kermit.Logger.v { message }
                }
            }
            level = LogLevel.ALL
        }

        defaultRequest {
            host = appBaseUrl
            header("Content-Type", "application/json")
            header("Content-Type", " text/event-stream")
            header("Accept", "text/event-stream")
            header("accept-language", "en-GB,en-US;q=0.9,en;q=0.8")
            header("accept-encoding", "gzip, deflate, br")
            header("referer", "https://chat.openai.com/chat")
            header("Origin", "https://chat.openai.com")
            header("Connection","keep-alive")
            header("Host","chat.openai.com")

        }
        install(HttpTimeout)
        followRedirects = true

    }

    suspend inline fun <reified T> GET(
        route: String,
        header: MutableMap<String, String>,
        queryPair: List<Pair<String, String>>? = null,
    ): T = httpClient.get {

        headers {
            header.forEach { (name, value) ->
                append(name, value)
            }
            header("Cross-Origin-Opener-Policy", "same-origin")
            header("Accept", "*/*")
            header("charset","utf-8")
            header("TE","trailers")
            header("Sec-Fetch-Mode","cors")
            header("Accept-Language" , "en-US,en;q=0.5")

        }
        accept(ContentType("application/json",""))
        url {
            host = appBaseUrl
            protocol = URLProtocol.HTTPS
            path(route)
            queryPair?.forEach { pair ->
                parameters.append(pair.first, pair.second)
            }
        }
    }.body() as T

    suspend inline fun <reified T, reified BODY> POST(
        route: String,
        queryPair: List<Pair<String, String>>? = null,
        body: BODY
    ): T = httpClient.post {
        url {
            host = appBaseUrl
            protocol = URLProtocol.HTTPS
            path(route)
            queryPair?.forEach { pair ->
                parameters.append(pair.first, pair.second)
            }
            setBody(body)
        }
        timeout { requestTimeoutMillis = Long.MAX_VALUE }
    }.body() as T

    @OptIn(InternalAPI::class)
    suspend inline fun <reified BODY> streamPost(
        route: String,
        queryPair: List<Pair<String, String>>? = null,
        body: BODY? = null,
        header: MutableMap<String, String>? = null,
        noinline onStreamed: suspend (String) -> Unit
    ) {
        httpClient.preparePost {
            headers {
                header?.forEach { (name, value) ->
                    append(name, value)
                }
            }

            timeout { requestTimeoutMillis = Long.MAX_VALUE }
            url {
                host = appBaseUrl
                protocol = URLProtocol.HTTPS
                path(route)
                queryPair?.forEach { pair ->
                    parameters.append(pair.first, pair.second)
                }
            }
            body?.let {
                setBody(it)
            }
        }.execute { httpResponse ->

            //val channel: ByteReadChannel = httpResponse.body()


            do {
                val line = httpResponse.content.readUTF8Line()
                onStreamed(line ?: "")
            }
            while (line != null)

    }
}

    @OptIn(InternalAPI::class)
    suspend inline fun <reified BODY> streamGet(
        route: String,
        queryPair: List<Pair<String, String>>? = null,
        body: BODY? = null,
        header: MutableMap<String, String>? = null,
        noinline onStreamed: suspend (ByteArray) -> Unit
    ) {
        httpClient.prepareGet {
            headers {
                header?.forEach { (name, value) ->
                    append(name, value)
                }
            }
            header("Cross-Origin-Opener-Policy", "same-origin")
            header("Accept", "*/*")
            header("charset","utf-8")
            header("TE","trailers")
            header("Sec-Fetch-Mode","cors")
            header("Accept-Language" , "en-US,en;q=0.5")


            timeout { requestTimeoutMillis = Long.MAX_VALUE }
            url {
                host = appBaseUrl
                protocol = URLProtocol.HTTPS
                path(route)
                queryPair?.forEach { pair ->
                    parameters.append(pair.first, pair.second)
                }
            }
            body?.let {
                setBody(it)
            }
        }.execute { httpResponse ->

            //val channel: ByteReadChannel = httpResponse.body()
            val responseBody: ByteArray = httpResponse.content.toByteArray()
            onStreamed(responseBody)


        }
    }


}

class CustomEncoder(override val name: String) : ContentEncoder {
    fun dzip(){

    }
    override fun CoroutineScope.decode(source: ByteReadChannel): ByteReadChannel {
        TODO("Not yet implemented")
    }

    override fun CoroutineScope.encode(source: ByteReadChannel): ByteReadChannel {
        TODO("Not yet implemented")
    }

}