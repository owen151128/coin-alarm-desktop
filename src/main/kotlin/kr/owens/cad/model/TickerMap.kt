package kr.owens.cad.model

import kr.owens.cad.util.CacheModule
import kr.owens.cad.util.Log
import kr.owens.cad.util.websocket.ExchangeWebSocketModule

/**
 * @author owen151128@gmail.com
 *
 * Created by owen151128 on 2022/09/04 22:39
 *
 * Providing features related to TickerList class
 */
class TickerMap : HashMap<String, Ticker>() {
    override fun putAll(from: Map<out String, Ticker>) {
        val result = super.putAll(from)

        ExchangeWebSocketModule.refreshWebSocket()

        return result
    }

    override fun put(key: String, value: Ticker): Ticker? {
        val result = super.put(key, value)

        ExchangeWebSocketModule.refreshWebSocket()

        return result
    }

    override fun remove(key: String): Ticker? {
        val result = super.remove(key)

        ExchangeWebSocketModule.refreshWebSocket()

        return result
    }
}
