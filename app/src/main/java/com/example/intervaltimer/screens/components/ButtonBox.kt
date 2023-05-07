package com.example.intervaltimer.screens.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.intervaltimer.navigation.Screen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ButtonBox(
    modifier: Modifier = Modifier,
    displaySecondText: Boolean,
    textLeft: String,
    textRight: String = "",
    color: Color = Color(0xFFE7EFFC),
    longClickAction: () -> Unit = {},
    navigate: () -> Unit
) {
    Button(
        modifier = modifier
            .height(40.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
            ,
        colors = ButtonDefaults.buttonColors(color),
        onClick = {},
        elevation = ButtonDefaults.elevation(0.dp),


        ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .combinedClickable(
                    onLongClick = longClickAction,
                    onClick = navigate
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = textLeft,
                modifier = Modifier.padding(start = 15.dp)
            )
            if (displaySecondText) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = textRight,
                    modifier = Modifier.padding(end = 15.dp),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}


@Composable
fun CancelButton(
    navController: NavController
) {


    ButtonBox(
        displaySecondText = false,
        textLeft = "Cancel",
        textRight = "",
        color = Color(0xFFFCE7E7)
    ) {
        navController.navigate(Screen.MainScreen.route) {
            popUpTo(0)
        }
    }

}


