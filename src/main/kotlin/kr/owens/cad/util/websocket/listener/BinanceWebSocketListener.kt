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
 * Created by owen151128 on 2022/09/08 18:19
 *
 * Providing features related to BinanceWebSocketListener class
 */
class BinanceWebSocketListener : ExchangeWebSocketListener() {
    override fun onMessageFromExchangeApi(response: JsonObject) {
        if (response.has("id")) {
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                val name = (response.get("s").asString
                    ?: throw JsonParseException("Ticker name not found!")).lowercase()
                val price = response.get("k").asJsonObject.get("c").asString
                    ?: throw JsonParseException("Current price not found!")
                ContentState.tickerMap[name]?.let {
                    it.currenPrice = "$%,f".format(price.toDouble())
                }
            }.onFailure { Log.e(it.stackTraceToString()) }
        }
    }

    override fun onCastJsonFailed(cause: Throwable) {
        Log.e("Json cast failed : $cause")
    }
}
