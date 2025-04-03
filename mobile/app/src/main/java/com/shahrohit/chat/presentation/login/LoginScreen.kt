package com.shahrohit.chat.presentation.login

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController : NavController) {
    Text(text = "Login Screen", style = MaterialTheme.typography.titleLarge)
}