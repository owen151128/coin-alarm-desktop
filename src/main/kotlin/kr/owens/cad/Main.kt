// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import kr.owens.cad.ResString
import kr.owens.cad.model.ContentState
import kr.owens.cad.style.icApp
import kr.owens.cad.util.getPreferredWindowSize
import kr.owens.cad.view.AppUi
import kr.owens.cad.view.SplashUi
import kotlin.concurrent.thread

@Composable
fun createWindow(
    content: ContentState,
    exitApplication: () -> Unit,
    icon: Painter,
    desiredHeight: Int,
    decorated: Boolean,
    mainUi: @Composable () -> Unit
) {
    Window(
        exitApplication,
        title = ResString.appName,
        state = WindowState(
            position = WindowPosition.Aligned(Alignment.Center),
            size = getPreferredWindowSize(800, desiredHeight)
        ),
        undecorated = !decorated,
        icon = icon
    ) {
        MaterialTheme {
            mainUi()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
fun main() = application {
    val content = remember { ContentState }

    val icon = icApp()

    val (desiredHeight, decorated) = if (content.isAppReady()) {
        Pair(1000, true)
    } else {
        Pair(300, false)
    }

    if (content.isAppReady()) {
        createWindow(content, ::exitApplication, icon, desiredHeight, decorated) {
            AppUi(content)
        }
    } else {
        createWindow(content, ::exitApplication, icon, desiredHeight, decorated) {
            SplashUi()
        }
    }
}
