package com.example.intervaltimer.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.intervaltimer.data.TimerEntity
import com.example.intervaltimer.screens.components.ButtonBox
import com.example.intervaltimer.screens.components.TopText
import com.example.intervaltimer.viewmodel.MainViewModelAbstract


@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModelAbstract
) {

    val timerList: MutableList<TimerEntity> = remember {
        mainViewModel.timerList.toMutableStateList()
    }
    val confirm: MutableState<Boolean> = remember { mutableStateOf(false) }
    var deleteTarget: TimerEntity? = null
    var deleteIndex: Int? = null


    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
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

                LazyColumn() {

                    items(timerList.size) { index ->

                        val timer = timerList[index]
                        val totalTime = timer.warmup!! + timer.coolDown!! + (timer.cycle!! * (timer.highIntensity!! + timer.lowIntensity!!))

                        val title = if (timer.timerTitle != "") {
                                        timer.timerTitle
                                    } else {
                                        "No Title"
                                    }

                        ButtonBox(
                            displaySecondText = true,
                            textLeft = title,
                            textRight = longToTime(totalTime),
                            longClickAction = {
                                confirm.value = true
                                deleteTarget = timer
                                deleteIndex = index

                            }

                        ) {
                            navController.navigate("timer")
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                    }
                }


            }
            if (confirm.value) {
                DeleteConfirm(
                    openDialog = confirm, timer = deleteTarget, index = deleteIndex,
                    mainViewModel = mainViewModel, timerList = timerList
                )

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
                    onClick = {
                        // mainViewModel.deleteAll()
                        navController.navigate("create")
                    },
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

@Composable
fun DeleteConfirm(
    openDialog: MutableState<Boolean>,
    timer: TimerEntity?,
    index: Int?,
    timerList: MutableList<TimerEntity>,
    mainViewModel: MainViewModelAbstract,

    ) {


    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false

            },
            title = {
                Text(text = "Confirm Delete")
            },
            text = {
                Text("Are you sure you want to delete?")
            },


            confirmButton = {
                Button(

                    onClick = {
                        openDialog.value = false

                    }) {
                    Text("No")
                }
            },
            dismissButton = {
                Button(

                    onClick = {
                        if (timer != null) {
                            mainViewModel.removeTimer(timer)
                        }
                        if (index != null) {
                            timerList.removeAt(index)
                        }
                        openDialog.value = false
                    }) {
                    Text("Yes")
                }
            }
        )
    }
}
