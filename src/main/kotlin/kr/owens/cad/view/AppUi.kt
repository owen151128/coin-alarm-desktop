package kr.owens.cad.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kr.owens.cad.model.ContentState
import kr.owens.cad.style.Gray

/**
 * @author owen151128@gmail.com
 *
 * Created by owen151128 on 2022/09/04 17:46
 *
 * Providing features related to AppUi class
 */
@Composable
fun AppUi(content: ContentState) {
    Surface(Modifier.fillMaxSize(), color = Gray) {
        MainScreen(content)
    }
}
