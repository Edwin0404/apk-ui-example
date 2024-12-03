package com.codevex.compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codevex.compose.ui.extensions.randomPastel

@Composable
@Preview
fun LazyColumnBasic(modifier: Modifier = Modifier) {
    val state = rememberLazyListState()
    var index by remember { mutableIntStateOf(0) }

    LaunchedEffect(state) {
        snapshotFlow { state.firstVisibleItemIndex }
            .collect {
                index = it
            }
    }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Icon(
                Icons.Filled.KeyboardArrowUp,
                null,
                Modifier.graphicsLayer {
                    alpha = if (state.canScrollBackward) 1f else 0f
                },
                Color.Red
            )
            Text(index.toString())
        }
        val items = (1..20).toList()
        LazyColumn(
            Modifier
                .weight(1f)
                .fillMaxWidth(), state
        ) {
            items(items) {
                val color = Color.randomPastel()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(color)
                ) {
                    Text(
                        "Item $it",
                        Modifier.align(Alignment.Center),
                        color = if (color.luminance() > 0.5f) Color.Black else Color.White
                    )
                }
            }
        }
        Icon(
            Icons.Filled.KeyboardArrowDown,
            null,
            Modifier.graphicsLayer {
                alpha = if (state.canScrollForward) 1f else 0f
            },
            Color.Red
        )
    }
}