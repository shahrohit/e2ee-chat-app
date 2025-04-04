package com.shahrohit.chat.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.shahrohit.chat.presentation.common.AppLink

@Composable
fun RegisterFooter(navController: NavController){
    fun onClick(){
        navController.popBackStack()
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Already have an account?", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
        AppLink(text = "Login", onClick = { onClick() })
    }
}