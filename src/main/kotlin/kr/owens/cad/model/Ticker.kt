package kr.owens.cad.model

/**
 * @author owen151128@gmail.com
 *
 * Created by owen151128 on 2022/09/04 21:14
 *
 * Providing features related to Ticker class
 */
data class Ticker(
    val exchange: Exchange,
    val ticker: String,
    var currenPrice: String,
    val min: Int,
    val max: Int
)
