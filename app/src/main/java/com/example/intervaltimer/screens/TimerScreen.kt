package com.example.intervaltimer.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import com.example.intervaltimer.screens.components.CancelButton
import com.example.intervaltimer.screens.components.TopText
import com.example.intervaltimer.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import java.lang.Math.*



@Composable
fun TimerScreen(
    totalTime: Long,
    navController: NavController,
    mainViewModel: MainViewModel
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF6F4F4)

    ) {
        Column {

            TopBarLabel()
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {

                    TimerDisplay(totalTime)

                    Spacer(modifier = Modifier.height(40.dp))

                    CurrentCycleIndicator()

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
    totalTime: Long,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 7.dp
) {

    var value by remember {
        mutableStateOf(1f)
    }

    var currentTime by remember {
        mutableStateOf(totalTime)
    }

    var isTimerRunning by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(
        key1 = currentTime,
        key2 = isTimerRunning
    ) {
        if (currentTime > 0 && isTimerRunning) {
            delay(100L)
            currentTime -= 100L
            value = currentTime / totalTime.toFloat()
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
                        currentTime = totalTime
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
private fun TopBarLabel() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopText(text = "Custom Name")
        Divider()

    }
}

@Composable
private fun CurrentCycleIndicator() {
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
            modifier = Modifier.size(40.dp)
        )

        Text(
            text = "High Intensity 1/10",
            modifier = Modifier.weight(1f),
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Back Arrow",
            modifier = Modifier.size(40.dp)
        )
    }
}


