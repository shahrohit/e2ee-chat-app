package com.shahrohit.chat.presentation.friends

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import com.shahrohit.chat.presentation.common.AppTextField
import com.shahrohit.chat.viewmodels.FriendViewModel

@Composable
fun SearchFriendScreen(viewModel: FriendViewModel = hiltViewModel()){
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Friends",style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(
            value = viewModel.query,
            onChange = viewModel::onQueryChange,
            placeholder = "Search...",
            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester)
        )
        Spacer(modifier = Modifier.height(16.dp))

        if(viewModel.checkingStatus == true) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally).size(20.dp))
        }
        else if(viewModel.checkingStatus == false){
            if(viewModel.users.isEmpty()) {
                Text("No users found", color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.align(Alignment.CenterHorizontally))
            }else {
                LazyColumn (verticalArrangement = Arrangement.spacedBy(5.dp)){
                    items(viewModel.users) { UserProfileCard(it) }
                }
            }
       }

    }
}