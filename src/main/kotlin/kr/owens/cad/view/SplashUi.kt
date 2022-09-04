package kr.owens.cad.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kr.owens.cad.ResString
import kr.owens.cad.style.PrimaryColor

/**
 * @author owen151128@gmail.com
 *
 * Created by owen151128 on 2022/09/04 18:00
 *
 * Providing features related to SplashUi class
 */
@Composable
fun SplashUi() {
    Box(Modifier.fillMaxSize().background(PrimaryColor)) {
        Text(
            ResString.appName,
            Modifier.align(Alignment.Center),
            color = Color.White,
            100.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
