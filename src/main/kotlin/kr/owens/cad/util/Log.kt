package kr.owens.cad.util

/**
 * @author owen151128@gmail.com
 *
 * Created by owen151128 on 2022/09/08 17:42
 *
 * Providing features related to Log class
 */
object Log {
    enum class Level(val tag: String) {
        DEBUG("OWEN"), INFO("*"), WARNING("!warn!"), ERROR("!error!")
    }

    private var logLevel = Level.INFO

    fun setLogLevel(logLevel: Level) {
        this.logLevel = logLevel
    }

    private fun printLog(logLevel: Level, message: String) {
        if (this.logLevel <= logLevel) {
            println("[${logLevel.tag}] $message")
        }
    }

    fun d(message: String) = printLog(Level.DEBUG, message)

    fun i(message: String) = printLog(Level.INFO, message)

    fun w(message: String) = printLog(Level.WARNING, message)

    fun e(message: String) = printLog(Level.ERROR, message)
}
