package com.shahrohit.chat.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun RegisterHeader() {
    Column {
        Text(text = "Create Account", style = MaterialTheme.typography.titleLarge)
        Text(text = "Create new account and connect to people.", color = MaterialTheme.colorScheme.onSurface)
    }
}