package com.codevex.compose.demos.gmail.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codevex.compose.demos.gmail.ui.details.Email
import com.github.javafaker.Faker

@SuppressLint("AutoboxingStateCreation")
@Composable
@Preview
fun Example() {
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
        val items = (1..100).toList()
        LazyColumn(
            Modifier
                .weight(1f)
                .fillMaxWidth(), state
        ) {
            items(items) {
                Text("Item is $it")
                Text(Faker().lorem().sentence())
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

@Composable
@Preview
fun GmailListItem(item: Email = Email(), clickListener: () -> Unit = {}) {
    var stared by remember(item.uid) { mutableStateOf(item.isFavourite) }

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { clickListener() }
        .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Image(
            painter = painterResource(item.from.avatar),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )

        Column(modifier = Modifier.padding(start = 12.dp)) {
            Row {
                Text(
                    text = item.from.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = item.date,
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 12.sp),
                )
            }

            Text(
                text = item.subject,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 14.sp),
                maxLines = 1,
            )

            Row {
                Text(
                    text = Faker().lorem().sentence(25),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                )

                IconButton(onClick = { stared = !stared }) {
                    Icon(
                        imageVector = if (stared) Icons.Default.Star else Icons.Default.StarBorder,
                        contentDescription = null,
                        tint = if (stared) Color.Yellow else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}


