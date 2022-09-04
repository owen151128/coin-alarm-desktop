package kr.owens.cad.model

/**
 * @author owen151128@gmail.com
 *
 * Created by owen151128 on 2022/09/04 22:39
 *
 * Providing features related to TickerList class
 */
class TickerList : ArrayList<Ticker>() {
    override fun add(element: Ticker): Boolean {
        println("add!")
        return super.add(element)
    }

    override fun remove(element: Ticker): Boolean {
        println("remove!")
        return super.remove(element)
    }
}
