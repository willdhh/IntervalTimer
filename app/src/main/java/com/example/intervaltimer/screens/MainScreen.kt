package com.example.intervaltimer.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.intervaltimer.data.TimerEntity
import com.example.intervaltimer.screens.components.ButtonBox
import com.example.intervaltimer.screens.components.TopText
import com.example.intervaltimer.viewmodel.MainViewModelAbstract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModelAbstract
) {

    val timerList = mainViewModel.timerListFlow

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column() {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(
                        start = 20.dp,
                        end = 20.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopText(text = "Interval Timer")

                Divider()

                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn(){
                }

                ButtonBox(
                    displaySecondText = true,
                    textLeft = "Training Interval 1",
                    textRight = "40:00",
                ) {
                    navController.navigate("timer")
                }

                Spacer(modifier = Modifier.height(20.dp))

                ButtonBox(
                    displaySecondText = true,
                    textLeft = "Training Interval 2",
                    textRight = "40:00",
                ) {
                    navController.navigate("timer")
                }

                Spacer(modifier = Modifier.height(20.dp))

                ButtonBox(
                    displaySecondText = true,
                    textLeft = "Training Interval 3",
                    textRight = "40:00",
                ) {
                    navController.navigate("timer")
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
            Row(
                modifier = Modifier
                    .padding(
                        bottom = 20.dp,
                        end = 20.dp
                    )
                    .align(Alignment.End)
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate("create") },
                    shape = CircleShape,
                    backgroundColor = Color(0xFF6AA3FF),
                    contentColor = Color.White
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Button"
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun PrevMain() {
    MainScreen(
        navController = rememberNavController(),
        mainViewModel = object : MainViewModelAbstract {
            override val timerListFlow: Flow<List<TimerEntity>>
                get() = flowOf(
                    listOf(
                        TimerEntity(
                            timerTitle = "title1",
                            warmup = 50000L,
                            highIntensity = 100000L,
                            lowIntensity = 20000L,
                            coolDown = 100000L
                        ),
                        TimerEntity(
                            timerTitle = "title2",
                            warmup = 50000L,
                            highIntensity = 100000L,
                            lowIntensity = 20000L,
                            coolDown = 100000L
                        ),
                        TimerEntity(
                            timerTitle = "title3",
                            warmup = 50000L,
                            highIntensity = 100000L,
                            lowIntensity = 20000L,
                            coolDown = 100000L
                        )

                    )
                )

            override fun addTimer(timer: TimerEntity) {
                TODO("Not yet implemented")
            }

            override fun removeTimer(timer: TimerEntity) {
                TODO("Not yet implemented")
            }
        }
    )
}


