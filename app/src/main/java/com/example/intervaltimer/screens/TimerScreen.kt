package com.example.intervaltimer.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.intervaltimer.data.TimerEntity
import com.example.intervaltimer.screens.components.CancelButton
import com.example.intervaltimer.screens.components.TopText
import com.example.intervaltimer.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.lang.Math.*


@Composable
fun TimerScreen(
    uid: String?,
    navController: NavController,
    mainViewModel: MainViewModel
) {
    var timer: TimerEntity
    runBlocking {
        timer = mainViewModel.getTimer(uid?.toLong())
    }

    val title = timer.timerTitle
    val warmup = timer.warmup!!
    val high = timer.highIntensity!!
    val low = timer.lowIntensity!!
    val cool = timer.coolDown!!
    val cycle = timer.cycle!!

    val intervalListName: MutableList<String> = mutableListOf()
    var intervalListVal: MutableList<Long> = mutableListOf()

    if (warmup != 0L) {
        intervalListName.add("Warm Up")
        intervalListVal.add(warmup)
    }
    for (i in 1..cycle) {
        if (high != 0L) {
            intervalListName.add("High Intensity $i / ${cycle}")
            intervalListVal.add(high)
        }
        if (low != 0L) {
            intervalListName.add("LowIntensity $i / ${cycle}")
            intervalListVal.add(low)
        }

    }
    if (cool != 0L) {
        intervalListName += listOf("Cool Down")
        intervalListVal += listOf(cool)
    }

    var currentIndex by remember {
        mutableStateOf(0)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF6F4F4)

    ) {
        Column {


            TopBarLabel(title)
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {

                    TimerDisplay(
                        intervalListVal = intervalListVal,
                        currentIndex = currentIndex
                    ) {
                        currentIndex += 1
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    CurrentCycleIndicator(
                        size = intervalListVal.size,
                        currentIndex = currentIndex,
                        intervalListName = intervalListName
                    ) {
                        currentIndex = it
                    }

                    Spacer(modifier = Modifier.height(80.dp))
                }
            }

            Column(
                modifier = Modifier.padding(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 30.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CancelButton(navController = navController)
            }
        }
    }
}

@Composable
private fun TimerDisplay(
    intervalListVal: List<Long>,
    currentIndex: Int,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 7.dp,
    incrementIndex: () -> Unit = {}
) {
    var currentTimeMax = intervalListVal[currentIndex]


    var oldIndex by remember {
        mutableStateOf(0)
    }

    var value by remember {
        mutableStateOf(1f)
    }

    var currentTime by remember {
        mutableStateOf(currentTimeMax)
    }

    var isTimerRunning by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(key1 = oldIndex, key2 = currentIndex) {
        if (currentIndex != oldIndex) {

            currentTime = intervalListVal[currentIndex]
            currentTimeMax = intervalListVal[currentIndex]
            oldIndex = currentIndex
            value = 1f
        }
    }

    LaunchedEffect(
        key1 = currentTime,
        key2 = isTimerRunning
    ) {

        if (currentTime > 0 && isTimerRunning) {
            delay(83L)
            currentTime -= 100L
            value = currentTime / currentTimeMax.toFloat()
        } else if (currentTime <= 0) {
            incrementIndex()

            currentTime = intervalListVal[currentIndex]
            currentTimeMax = intervalListVal[currentIndex]
            oldIndex = currentIndex
            value = 1f
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = modifier.size(250.dp)) {
                drawArc(
                    color = Color.LightGray,
                    startAngle = -215f,
                    sweepAngle = 250f,
                    useCenter = false,
                    style = Stroke(
                        strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                )
                drawArc(
                    color = Color.Green,
                    startAngle = -215f,
                    sweepAngle = 250f * value,
                    useCenter = false,
                    style = Stroke(
                        strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                )


            }
            Text(
                text = "${((currentTime + 999L) / 1000L) / 60}:${
                    String.format("%02d", floorMod((currentTime + 999L) / 1000L, 60))
                }",
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Button(
                onClick = {
                    if (currentTime <= 0L) {
                        currentTime = currentTimeMax
                        isTimerRunning = true
                    } else {
                        isTimerRunning = !isTimerRunning
                    }
                },
                modifier = Modifier.align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (!isTimerRunning || currentTime <= 0L) {
                        Color.Green
                    } else {
                        Color.Red
                    }
                )
            ) {
                Text(
                    text = if (isTimerRunning && currentTime > 0L) "Stop"
                    else "Start"
                )
            }
        }

    }
}


@Composable
private fun TopBarLabel(topLabel: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopText(text = topLabel)
        Divider()

    }
}

@Composable
private fun CurrentCycleIndicator(
    size: Int,
    currentIndex: Int,
    intervalListName: List<String>,
    clickAction: (Int) -> Unit = {}
) {
    var index by remember {
        mutableStateOf(0)
    }
    var label by remember {
        mutableStateOf(intervalListName[index])
    }

    LaunchedEffect(key1 = index, key2 = currentIndex) {
        label = intervalListName[index]
        if (index != currentIndex) {
            index = currentIndex
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 30.dp,
                end = 30.dp
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "Back Arrow",
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    if (index > 0) {
                        index--
                    }
                    clickAction(index)
                }
        )

        Text(
            text = label,
            modifier = Modifier.weight(1f),
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Back Arrow",
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    if (index < size - 1) {
                        index++
                    }
                    clickAction(index)
                }
        )
    }
}


