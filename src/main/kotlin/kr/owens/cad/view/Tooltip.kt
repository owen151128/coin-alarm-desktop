package kr.owens.cad.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * @author owen151128@gmail.com
 *
 * Created by owen151128 on 2022/09/04 14:50
 *
 * Providing features related to Tooltip class
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tooltip(text: String = "Tooltip", content: @Composable () -> Unit) {
    TooltipArea({
        Surface(
            color = Color(210, 210, 210),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text, Modifier.padding(10.dp), style = MaterialTheme.typography.caption)
        }
    }) {
        content()
    }
}
