package com.shahrohit.chat.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LoginHeader(){
    Column {
        Text(text = "Login Your Account", style = MaterialTheme.typography.titleLarge)
        Text(text = "Access your account and connect to people.", color = MaterialTheme.colorScheme.onSurface)
    }
}