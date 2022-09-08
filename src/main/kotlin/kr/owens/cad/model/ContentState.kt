package kr.owens.cad.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.owens.cad.util.CacheModule
import kr.owens.cad.util.Log

/**
 * @author owen151128@gmail.com
 *
 * Created by owen151128 on 2022/09/04 17:36
 *
 * Providing features related to ContentState class
 */
object ContentState {
    private val isContentReady = mutableStateOf(false)

    var tickerObserverState: MutableState<Boolean> = mutableStateOf(false)
    val tickerMap = TickerMap()

    fun isAppReady(): Boolean {
        return isContentReady.value
    }

    init {
        Log.setLogLevel(Log.Level.DEBUG)

        CoroutineScope(Dispatchers.IO).launch {
            CacheModule.getCachedTickerMap().onSuccess {
                tickerMap.putAll(it)
                isContentReady.value = true
            }.onFailure {
                Log.e("Get cached failed! cause : ${it.stackTraceToString()}")
                isContentReady.value = true
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                runCatching {
                    delay(100)
                    tickerObserverState.value = !tickerObserverState.value
                }
            }
        }
    }
}
