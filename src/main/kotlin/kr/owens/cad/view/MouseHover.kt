package kr.owens.cad.view

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerMoveFilter

/**
 * @author owen151128@gmail.com
 *
 * Created by owen151128 on 2022/09/04 14:59
 *
 * Providing features related to MouseHover class
 */
@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.hover(
    onEnter: () -> Boolean = { true },
    onExit: () -> Boolean = { true },
    onMove: (Offset) -> Boolean = { true }
): Modifier = this.pointerMoveFilter(onMove, onExit, onEnter)
