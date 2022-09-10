package kr.owens.cad.util

import androidx.compose.ui.res.useResource
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

/**
 * @author owen151128@gmail.com
 *
 * Created by owen151128 on 2022/09/10 20:10
 *
 * Providing features related to AlarmModule class
 */
object AlarmModule {
    private val clip: Clip = AudioSystem.getClip()

    init {
        useResource("sound/alarm.wav") {
            clip.open(AudioSystem.getAudioInputStream(it))
        }

        clip.loop(Clip.LOOP_CONTINUOUSLY)
    }

    fun playAlarm() = clip.start()

    fun stopAlarm() {
        clip.stop()
        clip.microsecondPosition = 0
    }
}
