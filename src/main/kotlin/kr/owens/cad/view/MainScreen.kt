package kr.owens.cad.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kr.owens.cad.ResString
import kr.owens.cad.model.ContentState
import kr.owens.cad.model.Exchange
import kr.owens.cad.model.Ticker
import kr.owens.cad.style.*

/**
 * @author owen151128@gmail.com
 *
 * Created by owen151128 on 2022/09/04 14:23
 *
 * Providing features related to MainScreen class
 */
@Composable
fun MainScreen(content: ContentState) {
    val tickerObserverState = remember { mutableStateOf(false) }
    content.tickerObserverState = tickerObserverState

    Column {
        TopContent(content, tickerObserverState)
        ScrollableArea(content, tickerObserverState)
    }
}

@Composable
fun TopContent(content: ContentState, tickerObserverState: MutableState<Boolean>) {
    TitleBar(ResString.appName, content, tickerObserverState)
    Spacer(modifier = Modifier.height(10.dp))
    Divider()
    Spacer(modifier = Modifier.height(5.dp))
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun tickerAddDialog(
    content: ContentState,
    showTickerDialog: MutableState<Boolean>,
    tickerObserverState: MutableState<Boolean>
) {
    val coinExchanges = Exchange.values().map { it.name.lowercase() }

    val focusRequester = remember { FocusRequester() }
    val (selectedExchange, onExchangeSelected) = remember { mutableStateOf(coinExchanges[0]) }
    val tickerInputState = remember { mutableStateOf("") }
    val minInputState = remember { mutableStateOf("") }
    val maxInputState = remember { mutableStateOf("") }

    AlertDialog({ showTickerDialog.value = false }, confirmButton = {
        Button({
            val min = minInputState.value.trim().toIntOrNull() ?: 0
            val max = maxInputState.value.trim().toIntOrNull() ?: 0
            val ticker = if (selectedExchange.trim().uppercase() == Exchange.BINANCE.name) {
                Ticker(
                    Exchange.BINANCE,
                    "${tickerInputState.value.trim().uppercase()}/USDT",
                    "$0",
                    min,
                    max
                )
            } else {
                Ticker(
                    Exchange.UPBIT,
                    "KRW-${tickerInputState.value.trim().uppercase()}",
                    "\u20A90",
                    min,
                    max
                )
            }

            content.tickerList.add(ticker)
            tickerObserverState.value = !tickerObserverState.value

            showTickerDialog.value = false
        }) { Text(ResString.add) }
    }, title = { Text(ResString.addDialogMessage) }, text = {
        Column {
            coinExchanges.forEach {
                Row(
                    Modifier.fillMaxWidth()
                        .selectable(it == selectedExchange, onClick = { onExchangeSelected(it) })
                ) {
                    RadioButton(
                        it == selectedExchange,
                        onClick = { onExchangeSelected(it) },
                        Modifier.align(Alignment.CenterVertically)
                    )
                    Text(
                        it,
                        Modifier.align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.body1.merge()
                    )
                }
            }
            OutlinedTextField(
                tickerInputState.value,
                { t -> tickerInputState.value = t },
                label = { Text(ResString.tickerInputHint) },
                modifier = Modifier.focusRequester(focusRequester)
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                minInputState.value,
                { t -> minInputState.value = t },
                label = { Text(ResString.minInputHint) })
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                maxInputState.value,
                { t -> maxInputState.value = t },
                label = { Text(ResString.maxInputHint) })
        }
    })
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
fun TitleBar(
    text: String,
    content: ContentState,
    tickerObserverState: MutableState<Boolean>
) {
    val refreshButtonHover = remember { mutableStateOf(false) }
    val showTickerDialog = remember { mutableStateOf(false) }

    TopAppBar(backgroundColor = PrimaryColor,
        title = {
            Row(Modifier.height(50.dp)) {
                Text(text, Modifier.weight(1f).align(Alignment.CenterVertically), Foreground)
                Surface(
                    modifier = Modifier.padding(end = 20.dp).align(Alignment.CenterVertically),
                    CircleShape,
                    Color.Transparent
                ) {
                    Tooltip(ResString.add) {
                        Clickable(
                            modifier = Modifier.hover(
                                onEnter = {
                                    refreshButtonHover.value = true
                                    false
                                },
                                onExit = {
                                    refreshButtonHover.value = false
                                    false
                                }
                            )
                                .background(color = if (refreshButtonHover.value) TranslucentBlack else Color.Transparent),
                            onClick = {
                                showTickerDialog.value = true
                            }
                        ) {
                            Image(icAdd(), null, Modifier.size(35.dp))
                        }
                    }
                }
            }
        }
    )

    if (showTickerDialog.value) {
        tickerAddDialog(content, showTickerDialog, tickerObserverState)
    }
}

@Composable
fun ScrollableArea(
    content: ContentState,
    tickerObserverState: MutableState<Boolean>
) {
    Box(Modifier.fillMaxSize().padding(end = 8.dp)) {
        val stateVertical = rememberScrollState(0)
        val stateHorizontal = rememberScrollState(0)

        Text(tickerObserverState.value.toString(), Modifier.alpha(0f))
        Column(Modifier.verticalScroll(stateVertical).align(Alignment.TopCenter)) {
            content.tickerList.forEach {
                Box(
                    Modifier.height(32.dp).fillMaxWidth(0.95f).background(Green)
                        .padding(start = 10.dp), Alignment.CenterStart
                ) {
                    Text("${it.ticker} / ${it.currenPrice} / ${it.max} / ${it.min}")
                    val removeButtonHover = remember { mutableStateOf(false) }
                    Surface(
                        modifier = Modifier.padding(end = 10.dp).align(Alignment.CenterEnd),
                        CircleShape,
                        Color.Transparent
                    ) {
                        Clickable(
                            modifier = Modifier.hover(
                                onEnter = {
                                    removeButtonHover.value = true
                                    false
                                },
                                onExit = {
                                    removeButtonHover.value = false
                                    false
                                }
                            )
                                .background(color = if (removeButtonHover.value) TranslucentBlack else Color.Transparent),
                            onClick = {
                                content.tickerList.remove(it)
                                content.tickerObserverState.value =
                                    !content.tickerObserverState.value
                            }
                        ) {
                            Image(icRemove(), null)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(stateVertical)
        )
        HorizontalScrollbar(
            modifier = Modifier.align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(end = 12.dp),
            adapter = rememberScrollbarAdapter(stateHorizontal)
        )
    }
}
