package com.shahrohit.chat.presentation.login

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
import com.shahrohit.chat.navigation.Screen

@Composable
fun LoginFooter(navController: NavController) {

    fun onClick(){
        navController.navigate(Screen.Register.route)
    }

    Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Don't have an Account?", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
        AppLink(text = "Create Account", onClick = { onClick() })
    }
}