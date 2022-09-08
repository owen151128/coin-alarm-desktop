package kr.owens.cad

/**
 * @author owen151128@gmail.com
 *
 * Created by owen151128 on 2022/09/04 14:46
 *
 * Providing features related to R class
 */

object ResString {
    val appName: String
    val add: String
    val cache: String
    val addDialogMessage: String
    val tickerInputHint: String
    val minInputHint: String
    val maxInputHint: String

    init {
        if (System.getProperty("user.language") == "ko") {
            appName = "코인 알리미"
            add = "코인 추가"
            cache = "코인 캐싱"
            addDialogMessage = "추가할 코인 정보 입력"
            tickerInputHint = "코인명을 입력해주세요 ex) btc"
            minInputHint = "얼마 이하가 되면 울릴까요? ex) 1234"
            maxInputHint = "얼마 이상이 되면 울릴까요? ex) 2123"

        } else {
            appName = "Coin alarm desktop"
            add = "Add ticker"
            cache = "Caching ticker"
            addDialogMessage = "Type ticker info"
            tickerInputHint = "Type ticker name ex) btc"
            minInputHint = "What if the price is below? ex) 1234"
            maxInputHint = "What if the price is higher? ex) 2123"
        }
    }
}
