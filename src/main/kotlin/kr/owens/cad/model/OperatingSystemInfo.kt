package kr.owens.cad.model

/**
 * @author owen151128@gmail.com
 *
 * Created by owen151128 on 2022/09/09 02:12
 *
 * Providing features related to Os class
 */
object OperatingSystemInfo {
    enum class OS(val id: String) {
        Linux("linux"),
        Windows("windows"),
        MacOS("macos")
    }

    val currentOS: OS by lazy {
        val os = System.getProperty("os.name")
        when {
            os.equals("Mac OS X", ignoreCase = true) -> OS.MacOS
            os.startsWith("Win", ignoreCase = true) -> OS.Windows
            os.startsWith("Linux", ignoreCase = true) -> OS.Linux
            else -> error("Unknown OS name: $os")
        }
    }
}
