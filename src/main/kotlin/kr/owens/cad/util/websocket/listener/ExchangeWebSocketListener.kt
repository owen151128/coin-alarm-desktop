package kr.owens.cad.util.websocket.listener

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kr.owens.cad.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

/**
 * @author owen151128@gmail.com
 *
 * Created by owen151128 on 2022/09/08 17:41
 *
 * Providing features related to ExchangeWebSocketListener class
 */
abstract class ExchangeWebSocketListener : WebSocketListener() {
    abstract fun onMessageFromExchangeApi(response: JsonObject)
    abstract fun onCastJsonFailed(cause: Throwable)

    private fun textToJsonElement(text: String) =
        JsonParser.parseString(text).asJsonObject ?: throw ClassCastException(CAST_FAILED_MESSAGE)

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)

        Log.d("Web socket closed : $code / $reason")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)

        Log.d("Web socket closing : $code / $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)

        Log.d("Web socket error : ${t.stackTraceToString()}")
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)

        Log.d("Web socket open!")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)

        Log.d("Message from exchange : $text")
        runCatching { onMessageFromExchangeApi(textToJsonElement(text)) }.onFailure {
            onCastJsonFailed(it)
        }
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)

        val text = String(bytes.toByteArray(), Charsets.UTF_8)

        Log.d("Message(Compression) form exchange : $text")
        runCatching { onMessageFromExchangeApi(textToJsonElement(text)) }.onFailure {
            onCastJsonFailed(it)
        }
    }

    companion object {
        const val CAST_FAILED_MESSAGE = "Response is not json element!"
    }
}
