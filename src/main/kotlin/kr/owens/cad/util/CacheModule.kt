package kr.owens.cad.util

import com.google.gson.GsonBuilder
import kr.owens.cad.exception.CacheNotAvailableException
import kr.owens.cad.model.OperatingSystemInfo
import kr.owens.cad.model.TickerMap
import java.io.File

/**
 * @author owen151128@gmail.com
 *
 * Created by owen151128 on 2022/09/09 01:04
 *
 * Providing features related to CacheModule class
 */
object CacheModule {
    private const val CACHE_NAME = "cad-cache-151128"
    private val gson = GsonBuilder().create()
    private val homeDirectoryPath = System.getProperty("user.home") ?: ""
    private val isCacheAvailable = homeDirectoryPath.isNotBlank()
    private val cacheFile by lazy {
        File(
            homeDirectoryPath,
            if (OperatingSystemInfo.currentOS == OperatingSystemInfo.OS.Windows) {
                CACHE_NAME
            } else {
                ".$CACHE_NAME"
            }
        )
    }

    private fun checkIsCacheAvailable() {
        if (!isCacheAvailable) {
            throw CacheNotAvailableException("home directory not found...")
        }
    }

    fun cacheTickerMap(tickerMap: TickerMap) = runCatching {
        checkIsCacheAvailable()

        if (cacheFile.exists()) {
            cacheFile.delete()
        }

        cacheFile.writeText(gson.toJson(tickerMap), Charsets.UTF_8)
    }

    fun getCachedTickerMap() = runCatching {
        checkIsCacheAvailable()

        if (!cacheFile.exists()) {
            return@runCatching TickerMap()
        }

        gson.fromJson(String(cacheFile.readBytes(), Charsets.UTF_8), TickerMap::class.java)
            ?: throw CacheNotAvailableException("Cast cached to TickerMap")
    }
}
