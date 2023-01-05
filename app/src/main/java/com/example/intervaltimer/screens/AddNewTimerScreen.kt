package com.example.intervaltimer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.intervaltimer.data.TimerEntity
import com.example.intervaltimer.screens.components.ButtonBox
import com.example.intervaltimer.screens.components.CancelButton
import com.example.intervaltimer.screens.components.TopText
import com.example.intervaltimer.viewmodel.MainViewModel
import com.example.intervaltimer.viewmodel.MainViewModelAbstract


@Composable
fun AddNewTimerScreen(navController: NavController,
mainViewModel: MainViewModelAbstract) {

    var title by remember {
        mutableStateOf("")
    }
    var warmup by remember {
        mutableStateOf(300000L)
    }
    var highIntensity by remember {
        mutableStateOf(120000L)
    }
    var lowIntensity by remember {
        mutableStateOf(120000L)
    }
    var coolDown by remember {
        mutableStateOf(300000L)
    }

    var cycle by remember {
        mutableStateOf(5)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF6F4F4)
        //        color = Color.Red

    ) {
        Column {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TopText(text = "New Interval Timer")
                    Divider()

                }

                Spacer(modifier = Modifier.height(30.dp))
                LazyColumn(
                    modifier = Modifier,
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxWidth()
                                .height(60.dp)
                                .padding(start = 40.dp),
                            contentAlignment = Alignment.CenterStart,

                            ) {
                            Row() {

                                BasicTextField(
                                    value = title,
                                    onValueChange = {
                                        title = it
                                    },
                                    textStyle = TextStyle(fontSize = 24.sp),
                                    singleLine = true,
                                    //                            keyboardOptions = ,
                                    //                            keyboardActions =
                                )

                            }
                            Text(
                                text = "Timer Name",
                                fontSize = 24.sp,
                                color = Color.LightGray
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .height(40.dp)
                                .padding(
                                    start = 40.dp,
                                    end = 40.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Warm Up")
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = longToTime(warmup),
                                modifier = Modifier.clickable { }
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .height(40.dp)
                                .padding(
                                    start = 40.dp,
                                    end = 40.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "High Intensity")
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = longToTime(highIntensity))
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .height(40.dp)
                                .padding(
                                    start = 40.dp,
                                    end = 40.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Low Intensity")
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = longToTime(lowIntensity))
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .height(40.dp)
                                .padding(
                                    start = 40.dp,
                                    end = 40.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BasicTextField(
                                value = "Cool Down",
                                onValueChange = {}
                            )
                            //                    Text(text = "Cool Down")
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = longToTime(coolDown))
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .height(40.dp)
                                .padding(
                                    start = 40.dp,
                                    end = 40.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BasicTextField(
                                value = "Cool Down",
                                onValueChange = {}
                            )
                            //                    Text(text = "# of Cycles")
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = "5")
                        }
                    }




                }
            }

            Column(
                modifier = Modifier
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 30.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                ButtonBox(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
                        .background(Color(0xFFE7EFFC)),
                    displaySecondText = false,
                    textLeft = "Save",
                    textRight = "",
                ) {
                    mainViewModel.addTimer(
                        TimerEntity(
                            timerTitle = title,
                            warmup = warmup,
                            highIntensity = highIntensity,
                            lowIntensity = lowIntensity,
                            coolDown = coolDown
                        )
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                CancelButton(navController = navController)

            }
        }

    }
}


fun longToTime(time: Long): String {
    return "${((time) / 1000L) / 60}:${
        String.format(
            "%02d",
            Math.floorMod(
                (time) / 1000L,
                60
            )
        )
    }"

}



