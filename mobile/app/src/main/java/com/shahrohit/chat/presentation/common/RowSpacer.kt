package com.shahrohit.chat.presentation.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RowSpacer(width : Int = 8) {
    Spacer(modifier = Modifier.width(width.dp))
}