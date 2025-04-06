package com.shahrohit.chat.presentation.verifyotp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun VerifyOtpHeader(){
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Verify Your Account", style = MaterialTheme.typography.titleLarge)
        Text(text = "We have send you an Email with an OTP to the register Email Address", color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center)
    }
}