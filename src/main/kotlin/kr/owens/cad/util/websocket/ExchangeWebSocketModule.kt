package kr.owens.cad.util.websocket

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kr.owens.cad.model.ContentState
import kr.owens.cad.model.Exchange
import kr.owens.cad.util.websocket.listener.BinanceWebSocketListener
import kr.owens.cad.util.websocket.listener.UpbitWebSocketListener
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.*

/**
 * @author owen151128@gmail.com
 *
 * Created by owen151128 on 2022/09/08 17:40
 *
 * Providing features related to ExchangeWebSocketModule class
 */
object ExchangeWebSocketModule {
    private const val BINANCE_PARAMS_KEY = "params"
    private const val UPBIT_CODES_KEY = "codes"
    private const val CLOSE_REASON = "Closed by user"
    private const val DELAY_TIME_MILLS = 1000L

    private val binanceRequest: Request
    private val upbitRequest: Request
    private val binanceRequestJson: JsonObject
    private val upbitTickerJson: JsonObject
    private val upbitRequestJson: JsonArray
    private val gson = Gson()

    private lateinit var binanceWebSocket: WebSocket
    private lateinit var upbitWebSocket: WebSocket

    init {
        Request.Builder().let {
            binanceRequest = it.url("wss://fstream.binance.com/ws").build()
            upbitRequest = it.url("wss://api.upbit.com/websocket/v1").build()
        }

        binanceRequestJson = JsonObject().apply {
            addProperty("method", "SUBSCRIBE")
            add(BINANCE_PARAMS_KEY, JsonArray())
            addProperty("id", 1)
        }

        upbitTickerJson = JsonObject().apply {
            addProperty("type", "ticker")
            add(UPBIT_CODES_KEY, JsonArray())
            addProperty("isOnlyRealtime", true)
        }

        upbitRequestJson = JsonArray().apply {
            add(JsonObject().apply { addProperty("ticket", UUID.randomUUID().toString()) })
            add(JsonObject().apply { addProperty("format", "SIMPLE") })
            add(upbitTickerJson)
        }
    }

    fun refreshWebSocket() {
        if (this::binanceWebSocket.isInitialized) {
            binanceWebSocket.close(1000, CLOSE_REASON)
        }

        if (this::upbitWebSocket.isInitialized) {
            upbitWebSocket.close(1000, CLOSE_REASON)
        }

        Thread.sleep(DELAY_TIME_MILLS)

        val binanceTickerList =
            ContentState.tickerMap.filterValues { it.exchange == Exchange.BINANCE }.map { it.key }
        val upbitTickerList =
            ContentState.tickerMap.filterValues { it.exchange == Exchange.UPBIT }.map { it.key }

        if (binanceTickerList.isNotEmpty()) {
            JsonArray().apply {
                binanceRequestJson.remove(BINANCE_PARAMS_KEY)
                binanceTickerList.forEach { add("$it@kline_1m") }
                binanceRequestJson.add(BINANCE_PARAMS_KEY, this)
            }

            OkHttpClient().let {
                binanceWebSocket = it.newWebSocket(binanceRequest, BinanceWebSocketListener())
                binanceWebSocket.send(gson.toJson(binanceRequestJson))
                it.dispatcher.executorService.shutdown()
            }
        }

        if (upbitTickerList.isNotEmpty()) {
            JsonArray().apply {
                upbitTickerJson.remove(UPBIT_CODES_KEY)
                upbitTickerList.forEach { add(it) }
                upbitTickerJson.add(UPBIT_CODES_KEY, this)
            }

            OkHttpClient().let {
                upbitWebSocket = it.newWebSocket(upbitRequest, UpbitWebSocketListener())
                upbitWebSocket.send(gson.toJson(upbitRequestJson))
                it.dispatcher.executorService.shutdown()
            }
        }
    }
}
