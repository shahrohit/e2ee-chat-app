package com.shahrohit.chat.presentation.friends

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun UserInformation(name: String, username: String) {
    Text(text = name,style = MaterialTheme.typography.titleMedium)
    Text(
        text = "@${username}",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurface
    )
}