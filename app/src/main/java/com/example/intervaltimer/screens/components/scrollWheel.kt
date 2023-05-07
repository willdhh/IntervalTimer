package com.example.intervaltimer.screens.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.LazyListSnapperLayoutInfo
import dev.chrisbanes.snapper.rememberLazyListSnapperLayoutInfo
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior


@OptIn(ExperimentalSnapperApi::class, ExperimentalFoundationApi::class)
@Composable
fun scrollWheel(
    isNotCycle: Boolean = true,
    returnTime: (Long?) -> Unit = {}
): Long? {

    val minList: List<Int?> = (0..99).toList()
    val secList: List<Int?> = (0..60).toList()

    val lazyListStateMinute = rememberLazyListState()
    val layoutInfoMinute = rememberLazyListSnapperLayoutInfo(lazyListStateMinute)

    val lazyListStateSec = rememberLazyListState()
    val layoutInfoSec = rememberLazyListSnapperLayoutInfo(lazyListStateSec)

    var returnLong: Long? = null


    var currentMinute by remember {
        mutableStateOf(minList[0])
    }
    var currentSec by remember {
        mutableStateOf(secList[0])
    }

    LaunchedEffect(lazyListStateMinute.isScrollInProgress) {

        if (!lazyListStateMinute.isScrollInProgress) {

            val snappedMinute = layoutInfoMinute.currentItem?.index

            currentMinute = snappedMinute


        }

    }
    LaunchedEffect(lazyListStateSec.isScrollInProgress) {

        if (!lazyListStateSec.isScrollInProgress) {

            val snappedSec = layoutInfoSec.currentItem?.index

            currentSec = snappedSec


        }
    }


    Column(
        modifier = Modifier.height(250.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier
                .height(250.dp)
                .border(border = BorderStroke(width = 3.dp, color = Color.Black))
                .background(Color.White)
        ) {

            Box(
                modifier = Modifier
                    .padding(top = 100.dp)
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(border = BorderStroke(width = 3.dp, color = Color.Black)),
                contentAlignment = Alignment.Center

            ) {
                if (isNotCycle) {
                    Text(text = " : ")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {

                LazyScroll(lazyListStateMinute, layoutInfoMinute, minList)

                if (isNotCycle) {
                    LazyScroll(lazyListStateSec, layoutInfoSec, secList)
                }
            }
        }

        Button(
            modifier = Modifier
                .background(Color.Red)
                .fillMaxWidth()
                .height(50.dp)
                .border(border = BorderStroke(width = 3.dp, color = Color.Black)),

            onClick = {
                val returnMinute = minList[currentMinute?.minus(2)!!]

                var totSec = returnMinute?.times(60)

                if (returnMinute != null) {
                    if (isNotCycle) {
                        val returnSec = minList[currentSec?.minus(2)!!]
                        if (totSec != null) {
                            totSec += returnSec!!
                            returnLong = totSec * 1000L
                        }
                    } else {
                        returnLong = returnMinute * 1L
                        Log.d("cycle", returnLong.toString())
                    }
                }

                returnTime(returnLong)

            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6AA3FF))


        ) {
            Text(text = "Set")
        }
    }
    return returnLong

}

@Composable
@OptIn(ExperimentalSnapperApi::class)
private fun LazyScroll(
    lazyListStateSec: LazyListState,
    layoutInfoSec: LazyListSnapperLayoutInfo,
    secList: List<Int?>
) {
    LazyColumn(
        modifier = Modifier.width(100.dp),
        contentPadding = PaddingValues(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = lazyListStateSec,
        flingBehavior = rememberSnapperFlingBehavior(
            layoutInfoSec
        ),

        ) {
        lazyBlankRow(41)
        lazyBlankRow(50)

        items(secList.size) { index ->

            Box(
                modifier = Modifier.height(50.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = secList[index].toString()
                )

            }


        }

        lazyBlankRow(50)
        lazyBlankRow(42)
    }
}


private fun LazyListScope.lazyBlankRow(height: Int) {
    item {

        Box(
            modifier = Modifier.height(height.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = ""
            )

        }

    }
}



