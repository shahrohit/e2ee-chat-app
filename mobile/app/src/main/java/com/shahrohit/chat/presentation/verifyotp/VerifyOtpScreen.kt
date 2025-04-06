package com.shahrohit.chat.presentation.verifyotp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun VerifyOtpScreen(navController: NavController, email: String, username: String){

    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        VerifyOtpHeader()
        Spacer(modifier = Modifier.height(20.dp))
        VerifyOtpBody(navController = navController, email = email, username = username)

    }
}