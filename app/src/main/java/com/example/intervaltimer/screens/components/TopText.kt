package com.example.intervaltimer.screens.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopText(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.height(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 32.sp,
            textAlign = TextAlign.Center

        )
    }
}