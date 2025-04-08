package com.shahrohit.chat.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shahrohit.chat.viewmodels.HomeViewModel

@Composable
fun HomeScreenBody(viewModel: HomeViewModel = hiltViewModel()) {
    val user = viewModel.userState.value

    if (user == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...")
        }
    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Welcome, ${user.name}", style = MaterialTheme.typography.headlineSmall)
            Text(text = "Email: ${user.email}")
            Button(
                onClick = viewModel::ping
            ) {
                Text(text ="Ping")
            }
        }
    }


}