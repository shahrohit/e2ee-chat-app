package com.shahrohit.chat.presentation.connection

import android.graphics.Color
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.shahrohit.chat.utils.ApiRequestState
import com.shahrohit.chat.viewmodels.ConnectionViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.shahrohit.chat.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun ConnectionScreen(navController: NavController, viewModel: ConnectionViewModel = hiltViewModel()){
    val context = LocalContext.current
    val connectionState by viewModel.connectionState

    LaunchedEffect(Unit) {
        viewModel.establishConnection(context)
    }

    LaunchedEffect(connectionState) {
        if(connectionState is ApiRequestState.Success){
            Toast.makeText(navController.context, "Connection Established", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Connection.route) { inclusive = true }
            }
        }
    }

    when(val state = connectionState){
        is ApiRequestState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Establishing the secure connection...")
                }
            }
        }
        is ApiRequestState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = state.message)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.establishConnection(context) }) {
                        Text("Retry")
                    }
                }
            }
        }

        ApiRequestState.Idle, is ApiRequestState.Success -> {}
    }

}