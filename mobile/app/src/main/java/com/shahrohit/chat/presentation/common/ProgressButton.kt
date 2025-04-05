package com.shahrohit.chat.presentation.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressButton(
    text : String,
    onClick : () -> Unit,
    modifier : Modifier,
    loading : Boolean,
) {
    Button(
    onClick = onClick,
    modifier = modifier,
    enabled = !loading,
    ) {
        Row (verticalAlignment = Alignment.CenterVertically){
            if (loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp).padding(end = 8.dp),
                    strokeWidth = 2.dp
                )
            }
            Text(text = text)
        }
    }
}