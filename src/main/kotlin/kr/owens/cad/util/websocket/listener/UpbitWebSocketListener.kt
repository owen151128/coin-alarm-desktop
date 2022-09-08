package kr.owens.cad.util.websocket.listener

import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.owens.cad.model.ContentState
import kr.owens.cad.util.Log

/**
 * @author owen151128@gmail.com
 *
 * Created by owen151128 on 2022/09/08 23:05
 *
 * Providing features related to UpbitWebSocketListener class
 */
class UpbitWebSocketListener : ExchangeWebSocketListener() {
    override fun onMessageFromExchangeApi(response: JsonObject) {
        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                val name = response.get("cd").asString
                    ?: throw JsonParseException("Ticker name not found!")
                val price = response.get("tp").asString
                    ?: throw JsonParseException("Current price not found!")
                ContentState.tickerMap[name]?.let {
                    it.currenPrice = "\u20A9%,f".format(price.toDouble())
                }
            }.onFailure { Log.e(it.stackTraceToString()) }
        }
    }

    override fun onCastJsonFailed(cause: Throwable) {
        Log.e("Json cast failed : $cause")
    }
}
