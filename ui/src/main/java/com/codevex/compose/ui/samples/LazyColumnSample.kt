package com.codevex.compose.ui.samples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codevex.compose.ui.components.LazyColumn
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
@Preview
fun LazyColumnScrollPosition(modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    val scrollPosition by rememberScrollPosition(listState)

    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Scroll Position: $scrollPosition",
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn(state = listState, modifier = Modifier.weight(1f))
    }
}

@Composable
fun rememberScrollPosition(listState: LazyListState): State<ScrollPosition> {
    return remember {
        derivedStateOf {
            when {
                listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0 -> ScrollPosition.TOP
                listState.canScrollForward.not() -> ScrollPosition.BOTTOM
                else -> ScrollPosition.MIDDLE
            }
        }
    }
}

enum class ScrollPosition {
    TOP, BOTTOM, MIDDLE
}

@Composable
@Preview
fun LazyColumnFirstIndex(modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    val index by rememberFirstVisibleIndex(listState)

    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("First visible index: $index", Modifier.padding(16.dp))
        LazyColumn(state = listState, modifier = Modifier.weight(1f))
    }
}

@Composable
fun rememberFirstVisibleIndex(listState: LazyListState): State<Int> {
    val firstVisibleIndex = remember { mutableIntStateOf(0) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }.distinctUntilChanged()
            .collect { firstVisibleIndex.intValue = it }
    }

    return firstVisibleIndex
}

@Composable
@Preview
fun LazyColumnTopButton(modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    val isAtTop by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
        }
    }

    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.End) {
        if (!isAtTop) {
            Text("Scroll to top", Modifier.padding(16.dp))
        }
        LazyColumn(state = listState, modifier = Modifier.weight(1f))
    }
}

@Composable
@Preview
fun LazyColumnScrollDirection(modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    val scrollDirection by rememberScrollDirection(listState)

    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Scroll Direction: $scrollDirection", modifier = Modifier.padding(16.dp)
        )
        LazyColumn(state = listState, modifier = Modifier.weight(1f))
    }
}

@Composable
fun rememberScrollDirection(listState: LazyListState): State<ScrollDirection> {
    val scrollDirection = remember { mutableStateOf(ScrollDirection.IDLE) }
    var previousIndex by remember { mutableIntStateOf(0) }
    var previousOffset by remember { mutableIntStateOf(0) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }.distinctUntilChanged()
            .collect { (currentIndex, currentOffset) ->
                scrollDirection.value = when {
                    currentIndex > previousIndex -> ScrollDirection.DOWN
                    currentIndex < previousIndex -> ScrollDirection.UP
                    currentOffset > previousOffset -> ScrollDirection.DOWN
                    currentOffset < previousOffset -> ScrollDirection.UP
                    else -> ScrollDirection.IDLE
                }
                previousIndex = currentIndex
                previousOffset = currentOffset
            }
    }

    return scrollDirection
}

enum class ScrollDirection {
    UP, DOWN, IDLE
}

@Composable
@Preview
fun LazyColumnTopBottomDetection(modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    val isAtTop by rememberIsAtTop(listState)
    val isAtBottom by rememberIsAtBottom(listState)
    val scrollDirection by rememberScrollDirection(listState)

    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Scroll Direction: $scrollDirection", modifier = Modifier.padding(8.dp)
        )
        Text(
            text = if (isAtTop) "You're at the top!" else if (isAtBottom) "You're at the bottom!" else "Keep scrolling!",
            modifier = Modifier.padding(8.dp),
            color = if (isAtTop || isAtBottom) Color.Green else Color.Gray
        )
        LazyColumn(state = listState, modifier = Modifier.weight(1f))
    }
}

@Composable
fun rememberIsAtTop(listState: LazyListState): State<Boolean> {
    val isAtTop = remember { mutableStateOf(true) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0 }.distinctUntilChanged()
            .collect { isAtTop.value = it }
    }

    return isAtTop
}

@Composable
fun rememberIsAtBottom(listState: LazyListState): State<Boolean> {
    val isAtBottom = remember { mutableStateOf(false) }

    LaunchedEffect(listState) {
        snapshotFlow {
            val lastIndex = listState.layoutInfo.totalItemsCount - 1
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem != null && lastVisibleItem.index == lastIndex && lastVisibleItem.offset + lastVisibleItem.size <= listState.layoutInfo.viewportEndOffset
        }.distinctUntilChanged().collect { isAtBottom.value = it }
    }

    return isAtBottom
}