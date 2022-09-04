package kr.owens.cad.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * @author owen151128@gmail.com
 *
 * Created by owen151128 on 2022/09/04 17:36
 *
 * Providing features related to ContentState class
 */
object ContentState {
    var tickerObserverState: MutableState<Boolean> = mutableStateOf(false)
    val tickerList = TickerList()

    fun isAppReady(): Boolean {
        return true
    }
}
