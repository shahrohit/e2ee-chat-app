package com.shahrohit.chat.presentation.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun AppLink(
    text : String,
    onClick : () -> Unit,
    modifier : Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    fontWeight: FontWeight = FontWeight.SemiBold
){
    TextButton(
        onClick = { onClick() },
        modifier = modifier
    ){
        Text(text = text, color = color, style = style,fontWeight = fontWeight)
    }
}