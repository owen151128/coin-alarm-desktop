package kr.owens.cad.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * @author owen151128@gmail.com
 *
 * Created by owen151128 on 2022/09/04 14:56
 *
 * Providing features related to Clickable class
 */
@Composable
fun Clickable(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    children: @Composable () -> Unit = {}
) {
    Box(modifier.clickable {
        onClick?.invoke()
    }) {
        children()
    }
}
