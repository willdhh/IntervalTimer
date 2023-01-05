package com.example.intervaltimer.screens.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun PrevCirc(){
    CircScroll(
        hourSize = 24,
        initialHour = 1
    )
}

@Composable
fun CircScroll(
    hourSize: Int,
    initialHour: Int
) {
    val height = 90.dp
    val cellSize = height / 3
    val cellTextSize = LocalDensity.current.run { (cellSize / 2f).toSp() }

    // just prepare an offset of 1 hour when format is set to 12hr format
    val hourOffset = if (hourSize == 12) 1 else 0
    val expandedSize = hourSize * 10_000_000
    val initialListPoint = expandedSize / 2
    val targetIndex = initialListPoint + initialHour - 1

    val scrollState = rememberLazyListState(targetIndex)
    val hour by remember { derivedStateOf { (scrollState.firstVisibleItemIndex + 1) % hourSize } }

    if (!scrollState.isScrollInProgress) {
        Log.e(
            "FocusedHour",
            "${hour + hourOffset}"
        )
    }

    LaunchedEffect(Unit) {
        // subtract the offset upon initial scrolling, otherwise it will look like
        // it moved 1 hour past the initial hour when format is set to 12hr format
        scrollState.scrollToItem(targetIndex - hourOffset)
    }

    Box(
        modifier = Modifier
            .height(height)
            .wrapContentWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .wrapContentWidth(),
            state = scrollState,
//            flingBehavior = rememberSnapFlingBehavior(lazyListState = scrollState)
        ) {
            items(
                expandedSize,
                itemContent = {

                    // if 12hr format, move 1 hour so instead of displaying 00 -> 11
                    // it will display 01 to 12
                    val num = (it % hourSize) + hourOffset
                    Box(
                        modifier = Modifier
                            .size(cellSize),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = String.format(
                                "%02d",
                                num
                            ),
                            style = MaterialTheme.typography.overline.copy(
                                color = Color.Gray,
                                fontSize = cellTextSize
                            )
                        )
                    }
                })
        }
    }
}
